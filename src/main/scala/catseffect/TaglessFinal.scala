package catseffect


import cats.effect.{ExitCode, IO, IOApp, Ref, Sync}
import cats._
import cats.implicits._

/*
  Example to demonstrate tagless final pattern
 */

class TaglessFinal extends IOApp {

  case class Product(name: String, noOfItems: Int)
  object Product {
    implicit val showProduct: Show[Product] =
      Show.show(user => s"${user.name} is ${user.noOfItems} years old")
  }

  trait productRepository[F[_]] {
    def addProduct(user: Product): F[Unit]
    def getProducts: F[List[Product]]
  }

  object productRepository {
    def impl[F[_]: Sync]: F[productRepository[F]] =
      Ref.of[F, Map[String, Product]](Map()).map { ref =>
        new productRepository[F] {
          override def addProduct(product: Product): F[Unit] =
            ref.update{ productMap =>
              productMap + (product.name -> product)
            }

          override def getProducts: F[List[Product]] =
            ref.get.map(productMap => productMap.values.toList)
        }
      }
  }

  trait ProductService[F[_]] {
    def printProducts(products: List[Product]): F[Unit]
  }

  object ProductService {
    def impl[F[_]: Sync: Parallel]: ProductService[F] = new ProductService[F] {
      override def printProducts(products: List[Product]): F[Unit] =
        products.parTraverse_(product => Sync[F].delay(println(product.show)))
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      productRepository      <- productRepository.impl[IO]
      productService         = ProductService.impl[IO]
      users                  = List(Product("user1", 52), Product("user2", 53))
      _                      <- users.parTraverse_(productRepository.addProduct)
      savedProducts          <- productRepository.getProducts
      _                      <- productService.printProducts(savedProducts)
    } yield ExitCode.Success
  }
}

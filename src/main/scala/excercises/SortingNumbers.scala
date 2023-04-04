package excercises

object SortingNumbers extends App {

  implicit val order: Ordering[Int] = Ordering.fromLessThan{ (a, b) =>

    val aString = a.toString
    val bString = b.toString

    (aString + bString).compareTo(bString + aString) >= 0
  }

  implicit val orderByage: Ordering[Users] = Ordering.fromLessThan{ (a, b) =>
    a.age.compareTo(b.age) >= 0
  }

  val li = List(3,5,6,23,9)

  case class Users(name: String, age: Int)
  val users = List(Users("a",2),Users("b",2), Users("b",1),Users("b",23))

  println(li.sorted.mkString(""))
  println(users.sorted)
  println(users.sortBy(u => (u.name, u.age)))

}

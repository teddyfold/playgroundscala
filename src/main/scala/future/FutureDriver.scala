package future

/**
  * Created by zhilin on 10/2/16.
  */
object FutureDriver extends App{

  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.util.{Success, Failure}

  val sumFuture=Future {
    //let thread sleep for 1 second


    //perform summation
    12 + 25
  }

  //only support 2.12
// val transformSum= sumFuture.transform {
//   case Success(res) => Success(res * res)
//   case Failure(ex) => Failure(ex)
// }

  val  exceptionFuture = Future {
    Thread.sleep(2000)
    100/0
  }


  //println(sumFuture.value)




  //success value returned
  //will return NONE if main thread is not slept
  sumFuture.foreach(x => println("Sum Future ForEach "+x))

  sumFuture.onComplete {
    case Success(res) => println("sumFuture Complete Callback "+res )
    case Failure(ex) => println(ex)

  }

  exceptionFuture.onComplete {
    case Success(res) => println("exception future Complete Callback "+res * res)
    case Failure(ex) => println("exception future failure: "+ex)

  }



  val doubleSum=sumFuture.map(_ * 2)

 val newSum:Future[Int]= for(
    sum1 <- sumFuture;
    sum2 <- sumFuture
  ) yield sum1 * sum2

  doubleSum.foreach( doubleValue => println(s"Double Sum = $doubleValue"))


  newSum.foreach( doubleValue => println(s"Square Sum value = $doubleValue"))



  val googleString=Future{
    scala.io.Source.fromURL("http://www.google.com").mkString
  }

  val yahooString=Future{
    scala.io.Source.fromURL("http://www.yahoo.com").mkString
  }


  googleString.foreach(println(_))
  //yahooString.foreach(println(_))

  //success value returned
  Thread.sleep(10000)






}

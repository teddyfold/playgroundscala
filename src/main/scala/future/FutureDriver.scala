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
    Thread.sleep(5000)

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
  sumFuture.foreach(x => println("ForEach "+x+1))

  sumFuture.onComplete {
    case Success(res) => println("sumFuture Complete Callback "+res * res)
    case Failure(ex) => println(ex)

  }

  exceptionFuture.onComplete {
    case Success(res) => println("exception future Complete Callback "+res * res)
    case Failure(ex) => println("exception future failure: "+ex)

  }



  //success value returned
  Thread.sleep(6000)








  //error result not returned
  val exceptionValue=exceptionFuture.foreach(x => x)



}

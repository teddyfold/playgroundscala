package reactive

/**
  * Created by zhilin on 7/26/16.
  */

import base._
import akka.actor._



case class Message(s: String)

case class Register(server: ActorRef)

class MessageClient extends Actor{
  val actorName = self.path.name
   def receive = {
     case Message(content) => System.out.println(s"$self Received from $sender $content")

     case Register(server) =>
        System.out.println(s"Register $self with "+server)
        server ! Message("Ping")
     case _ => System.out.println("Client unable to handle")
   }
}


class MessageServer extends Actor{
  val actorName = self.path.name

  def receive={
    case Message(content) =>
        System.out.println(s"Server Received From ${context.sender()} $content")
        sender ! Message("Pong")
        EchoDriver.completedStep()


    case _ => System.out.println("Server Unable to handle ")
  }
}
object EchoDriver extends CompletableApp(100){
    //Message Server
    val server=system.actorOf(Props[MessageServer], "Server")

    for(i<- 1 to 100){

      val client=system.actorOf(Props[MessageClient], s"Client_$i")
      client ! Register(server)

    }


    awaitCompletion


}

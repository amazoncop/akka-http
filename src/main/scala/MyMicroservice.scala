import akka.actor.ActorSystem
import akka.event.{LoggingAdapter, Logging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorFlowMaterializer, FlowMaterializer}
import com.typesafe.config.{Config, ConfigFactory}
import scala.concurrent.{Future, ExecutionContextExecutor}
import spray.json.DefaultJsonProtocol

case class UppercaseReply(string: String, reply: String, characters: List[String])

trait MyProtocols extends DefaultJsonProtocol {
  implicit val UppercaseReplyFormat = jsonFormat3(UppercaseReply.apply)
}

trait MyService extends MyProtocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: FlowMaterializer

  def upper(string: String) = string.toUpperCase

  def config: Config
  val logger: LoggingAdapter

  val routes = {
    logRequestResult("my-microservice") {
      pathPrefix("upper") {
        (get & path(Segment)) { string =>
          complete {
            Future.successful(UppercaseReply(string, upper(string), upper(string).split("").toList.tail))
          }
        }
      }
    }
  }
}

object MyMicroservice extends App with MyService {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorFlowMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
}
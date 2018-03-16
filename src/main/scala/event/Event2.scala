package event

import extract.Extract.ExtractIdentity
import play.api.libs.json.{Format, Json}

case class Event2(imo: IMO) extends Event
object Event2 {
  implicit def extractIMO: ExtractIdentity[Event2, IMO] = _.imo
  implicit val event2: Format[Event2] = Json.format[Event2]
}
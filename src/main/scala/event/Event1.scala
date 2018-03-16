package event

import extract.Extract.ExtractOption
import play.api.libs.json.{Format, Json}

case class Event1(data: String, imo: Option[IMO]) extends Event
object Event1 {
  implicit def extractIMO: ExtractOption[Event1, IMO] = _.imo
  implicit val event1: Format[Event1]                 = Json.format[Event1]
}

package event

import play.api.libs.json.{Format, Json}

case class IMO(value: String)

object IMO{
  implicit val format: Format[IMO] = Json.format
}
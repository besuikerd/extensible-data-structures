import extract.Extract
import extract.Extract.{CoExtract, ExtractIdentity, Identity}
import play.api.libs.json.{JsResult, JsValue, Reads, Writes}

object PlayJsonInterop {
  implicit def extractWrites[T](implicit writes: Writes[T]): Extract[T, JsValue, Identity] = writes.writes _
  implicit def extractReads[T](implicit reads: Reads[T]): CoExtract[T, JsValue, JsResult]  = reads.reads _
}

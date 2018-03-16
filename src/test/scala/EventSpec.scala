import event._
import extract.Extract._
import PlayJsonInterop._
import com.iravid.playjsoncats.implicits._
import org.scalatest.{FlatSpec, GivenWhenThen, MustMatchers}
import play.api.libs.json.{JsResult, JsSuccess, JsValue}

class EventSpec extends FlatSpec with GivenWhenThen with MustMatchers {
  "VisitEvent" must "correctly serialize/deserialize events" in {
    Given("A list of events")

    val events: Seq[Event] = Seq(
      Event2(IMO("42")),
      Event1("dadsa", Some(IMO("43"))),
      Event1("bla", None)
    )

    When("We serialize the events to json with VisitEvent")
    val eventsToJson = events.map(VisitEvent[Identity, JsValue](_))

    When("We serialize the events to json with pattern matching")
    val eventsToJsonPatternMatch = events.map {
      case e: Event1 => Event1.event1.writes(e)
      case e: Event2 => Event2.event2.writes(e)
    }

    Then("We should obtain the same results using a pattern match")
    eventsToJson mustBe eventsToJsonPatternMatch

    When("We deserialize the events from json with VisitEvent")
    val jsonToEvents = eventsToJson.map(VisitEvent[JsResult, JsValue].coapply(_))

    Then("The deserialized values must match the list of events")
    val collectedEvents = jsonToEvents.collect { case JsSuccess(e, _) => e }
    collectedEvents mustBe events

  }

  it must "Correctly extract imos from a list of events" in {
    Given("A list of events")

    val events: Seq[Event] = Seq(
      Event2(IMO("42")),
      Event1("dadsa", Some(IMO("43"))),
      Event1("bla", None)
    )

    When("We extract the imos from the events with VisitEvent")
    val imos = events.flatMap(VisitEvent[Option, IMO](_))

    Then("The imos should match the expected list")
    imos mustBe Seq(IMO("42"), IMO("43"))
  }
}

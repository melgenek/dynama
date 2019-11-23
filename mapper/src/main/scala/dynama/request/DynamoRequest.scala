package dynama.request

import dynama.converter.CompositeConverter
import software.amazon.awssdk.services.dynamodb.model.DynamoDbRequest

final case class DynamoRequest[REQ <: DynamoDbRequest.Builder](builder: REQ) {

  def map(builderMutation: REQ => REQ): DynamoRequest[REQ] = {
    DynamoRequest(builderMutation(builder))
  }

  def withResponse[RES](implicit converter: CompositeConverter[RES]): DynamoRequestWithResponse[REQ, RES] = {
    DynamoRequestWithResponse[REQ, RES](builder)
  }

}

final case class DynamoRequestWithResponse[REQ <: DynamoDbRequest.Builder, RES](builder: REQ)
                                                                               (implicit val itemConverter: CompositeConverter[RES]) {

  def map(builderMutation: REQ => REQ): DynamoRequestWithResponse[REQ, RES] = {
    DynamoRequestWithResponse(builderMutation(builder))
  }

  def withResponse[NEW_RES](implicit converter: CompositeConverter[NEW_RES]): DynamoRequestWithResponse[REQ, NEW_RES] = {
    DynamoRequestWithResponse[REQ, NEW_RES](builder)
  }

}
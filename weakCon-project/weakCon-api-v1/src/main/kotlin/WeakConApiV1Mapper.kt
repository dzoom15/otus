import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.otus.otuskotlin.marketplace.api.v1.models.ICategoryRequest
import com.otus.otuskotlin.marketplace.api.v1.models.ICategoryResponse
import com.otus.otuskotlin.marketplace.api.v1.models.IRequest
import com.otus.otuskotlin.marketplace.api.v1.models.IResponse

object WeakConApiV1Mapper {

    val apiV1Mapper = JsonMapper.builder().run {
        enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
        build()
    }
    @Suppress("unused")
    fun apiV1RequestSerialize(request: Any): String = apiV1Mapper.writeValueAsString(request)

    @Suppress("UNCHECKED_CAST", "unused")
    fun <T : ICategoryRequest> apiV1ICategoryRequestDeserialize(json: String): T =
        apiV1Mapper.readValue(json, ICategoryRequest::class.java) as T

    @Suppress("UNCHECKED_CAST", "unused")
    fun <T : IRequest> apiV1IRequestDeserialize(json: String): T =
        apiV1Mapper.readValue(json, IRequest::class.java) as T

    @Suppress("unused")
    fun apiV1ResponseSerialize(response: Any): String = apiV1Mapper.writeValueAsString(response)

    @Suppress("UNCHECKED_CAST", "unused")
    fun <T : IResponse> apiV1ResponseDeserialize(json: String): T =
        apiV1Mapper.readValue(json, IResponse::class.java) as T

    @Suppress("UNCHECKED_CAST", "unused")
    fun <T : ICategoryResponse> apiV1ICategoryResponseDeserialize(json: String): T =
        apiV1Mapper.readValue(json, ICategoryResponse::class.java) as T

}
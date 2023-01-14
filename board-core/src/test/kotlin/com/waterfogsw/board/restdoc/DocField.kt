package com.waterfogsw.board.restdoc

import DocsFieldType
import org.springframework.restdocs.payload.*

class RestDocField(
  val descriptor: FieldDescriptor
) {

  infix fun isOptional(value: Boolean): RestDocField {
    if (value) descriptor.optional()
    return this
  }

  infix fun isIgnored(value: Boolean): RestDocField {
    if (value) descriptor.ignored()
    return this
  }

  infix fun means(value: String): RestDocField {
    descriptor.description(value)
    return this
  }

  infix fun attributes(block: RestDocField.() -> Unit): RestDocField {
    block()
    return this
  }
}

infix fun String.type(
  docsFieldType: DocsFieldType
): RestDocField {
  return createField(this, docsFieldType.type)
}

private fun createField(
  value: String,
  type: JsonFieldType,
  optional: Boolean = true
): RestDocField {
  val descriptor = PayloadDocumentation
      .fieldWithPath(value)
      .type(type)
      .description("")

  if (optional) descriptor.optional()

  return RestDocField(descriptor)
}

fun requestBody(vararg fields: RestDocField): RequestFieldsSnippet =
  PayloadDocumentation.requestFields(fields.map { it.descriptor })

fun responseBody(vararg fields: RestDocField): ResponseFieldsSnippet =
  PayloadDocumentation.responseFields(fields.map { it.descriptor })

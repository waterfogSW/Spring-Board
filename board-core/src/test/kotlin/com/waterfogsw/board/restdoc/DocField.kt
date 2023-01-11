package com.waterfogsw.board.restdoc

import DocsFieldType
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet

open class RestDocField(
  val descriptor: FieldDescriptor
) {

  open infix fun isOptional(value: Boolean): RestDocField {
    if (value) descriptor.optional()
    return this
  }

  open infix fun isIgnored(value: Boolean): RestDocField {
    if (value) descriptor.ignored()
    return this
  }

  open infix fun means(value: String): RestDocField {
    descriptor.description(value)
    return this
  }

  open infix fun attributes(block: RestDocField.() -> Unit): RestDocField {
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

fun <T : RestDocField> requestBody(vararg fields: T): RequestFieldsSnippet =
  PayloadDocumentation.requestFields(fields.map { it.descriptor })

fun <T : RestDocField> responseBody(vararg fields: T): ResponseFieldsSnippet =
  PayloadDocumentation.responseFields(fields.map { it.descriptor })

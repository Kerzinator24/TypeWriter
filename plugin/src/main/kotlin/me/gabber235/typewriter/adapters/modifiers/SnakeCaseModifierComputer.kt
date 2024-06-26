package me.gabber235.typewriter.adapters.modifiers

import me.gabber235.typewriter.adapters.FieldInfo
import me.gabber235.typewriter.adapters.FieldModifier
import me.gabber235.typewriter.adapters.FieldModifier.StaticModifier
import me.gabber235.typewriter.adapters.PrimitiveField
import me.gabber235.typewriter.adapters.PrimitiveFieldType
import me.gabber235.typewriter.logger


@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
annotation class SnakeCase

object SnakeCaseModifierComputer : StaticModifierComputer<SnakeCase> {
    override val annotationClass: Class<SnakeCase> = SnakeCase::class.java

    override fun computeModifier(annotation: SnakeCase, info: FieldInfo): FieldModifier? {
        // If the field is wrapped in a list or other container we try if the inner type can be modified
        innerCompute(annotation, info)?.let { return it }

        if (info !is PrimitiveField) {
            logger.warning("SnakeCase annotation can only be used on strings (including in lists or maps)!")
            return null
        }
        if (info.type != PrimitiveFieldType.STRING) {
            logger.warning("SnakeCase annotation can only be used on strings (including in lists or maps)!")
            return null
        }

        return StaticModifier("snake_case")
    }
}
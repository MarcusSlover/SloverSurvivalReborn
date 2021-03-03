package me.marcusslover.sloversurvivalreborn.code;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PatchVersion {
    String version() default "1.0.0";
}

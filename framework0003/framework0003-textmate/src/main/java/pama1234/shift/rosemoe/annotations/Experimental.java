package pama1234.shift.rosemoe.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Experimental{
}

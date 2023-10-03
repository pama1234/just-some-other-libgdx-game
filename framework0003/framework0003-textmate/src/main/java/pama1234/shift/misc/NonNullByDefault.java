package pama1234.shift.misc;

import static org.eclipse.jdt.annotation.DefaultLocation.FIELD;
import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_ARGUMENT;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_BOUND;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.eclipse.jdt.annotation.DefaultLocation;

@Documented
@Retention(RetentionPolicy.CLASS)
public @interface NonNullByDefault{
  DefaultLocation[] value() default {PARAMETER,RETURN_TYPE,FIELD,TYPE_BOUND,TYPE_ARGUMENT};
}

package org.eclipse.tm4e.core.internal.theme.css;

import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CombinatorCondition;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionFactory;
import org.w3c.css.sac.ContentCondition;
import org.w3c.css.sac.LangCondition;
import org.w3c.css.sac.NegativeCondition;
import org.w3c.css.sac.PositionalCondition;

import pama1234.shift.misc.NonNullByDefault;

@NonNullByDefault({})
public final class CSSConditionFactory implements ConditionFactory{
  public static final ConditionFactory INSTANCE=new CSSConditionFactory();
  @Override
  public AttributeCondition createClassCondition(final String namespaceURI,final String value) throws CSSException {
    return new CSSClassCondition(null,"class",value);
  }
  @Override
  public AttributeCondition createAttributeCondition(final String localName,final String namespaceURI,
    final boolean specified,final String value) throws CSSException {
    return new CSSAttributeCondition(localName,namespaceURI,specified,value);
  }
  @Override
  public CombinatorCondition createAndCondition(final Condition first,final Condition second) throws CSSException {
    return new CSSAndCondition((ExtendedCondition)first,(ExtendedCondition)second);
  }
  @Override
  public AttributeCondition createBeginHyphenAttributeCondition(final String arg0,final String arg1,
    final boolean arg2,final String arg3) throws CSSException {
    throw new CSSException("Not implemented in CSS2");
  }
  @Override
  public ContentCondition createContentCondition(final String arg0) throws CSSException {
    throw new CSSException("Not implemented in CSS2");
  }
  @Override
  public AttributeCondition createIdCondition(final String arg0) throws CSSException {
    throw new CSSException("Not implemented in CSS2");
  }
  @Override
  public LangCondition createLangCondition(final String arg0) throws CSSException {
    throw new CSSException("Not implemented in CSS2");
  }
  @Override
  public NegativeCondition createNegativeCondition(final Condition arg0) throws CSSException {
    throw new CSSException("Not implemented in CSS2");
  }
  @Override
  public AttributeCondition createOneOfAttributeCondition(final String arg0,final String arg1,final boolean arg2,
    final String arg3) throws CSSException {
    throw new CSSException("Not implemented in CSS2");
  }
  @Override
  public Condition createOnlyChildCondition() throws CSSException {
    throw new CSSException("Not implemented in CSS2");
  }
  @Override
  public Condition createOnlyTypeCondition() throws CSSException {
    throw new CSSException("Not implemented in CSS2");
  }
  @Override
  public CombinatorCondition createOrCondition(final Condition arg0,final Condition arg1) throws CSSException {
    throw new CSSException("Not implemented in CSS2");
  }
  @Override
  public PositionalCondition createPositionalCondition(final int arg0,final boolean arg1,final boolean arg2)
    throws CSSException {
    throw new CSSException("Not implemented in CSS2");
  }
  @Override
  public AttributeCondition createPseudoClassCondition(final String arg0,final String arg1) throws CSSException {
    throw new CSSException("Not implemented in CSS2");
  }
}

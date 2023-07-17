package org.eclipse.tm4e.ui.internal.themes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.ui.themes.IThemeAssociation;

final class BaseThemeAssociationRegistry{
  @Nullable
  private IThemeAssociation defaultAssociation;
  private final Map<String,@Nullable List<IThemeAssociation>> eclipseThemeIds=new HashMap<>();
  private final List<IThemeAssociation> allAssociations=new ArrayList<>();
  void register(final IThemeAssociation association) {
    //String eclipseThemeId = association.getEclipseThemeId();
    // when association is marked as default or scope name is defined,
    // update the default association or association for a given E4 Theme.
    allAssociations.clear();
  }
  void unregister(final IThemeAssociation association) {
    //String eclipseThemeId = association.getEclipseThemeId();
    //		Collection<IThemeAssociation> associations = eclipseThemeIds.get(eclipseThemeId);
    //		if (associations != null) {
    //			for (IThemeAssociation a : associations) {
    //				if (a.equals(association)) {
    //					associations.remove(a);
    //					break;
    //				}
    //			}
    //		}
    allAssociations.clear();
  }
  @Nullable
  IThemeAssociation getThemeAssociationFor(final String eclipseThemeId) {
    final List<IThemeAssociation> associations=eclipseThemeIds.get(eclipseThemeId);
    if(associations!=null) {
      if(associations.size()==1) {
        return associations.get(0);
      }
    }
    return null;
  }
  IThemeAssociation[] getThemeAssociations(final boolean isDefault) {
    return getThemeAssociations().toArray(IThemeAssociation[]::new);
  }
  @Nullable
  IThemeAssociation getDefaultAssociation() {
    return defaultAssociation;
  }
  IThemeAssociation[] getThemeAssociationsForTheme(final String themeId) {
    return getThemeAssociations().stream().filter(themeAssociation->themeId.equals(themeAssociation.getThemeId()))
      .toArray(IThemeAssociation[]::new);
  }
  boolean hasThemeAssociationsForTheme(final String themeId,final String eclipseThemeId) {
    //		Collection<IThemeAssociation> associations = eclipseThemeIds.get(eclipseThemeId);
    //		if (associations != null) {
    //			for (IThemeAssociation themeAssociation : associations) {
    //				if (themeId.equals(themeAssociation.getThemeId())) {
    //					return eclipseThemeId.equals(themeAssociation.getEclipseThemeId());
    //				}
    //			}
    //			return false;
    //		} else {
    //			Set<Entry<String, List<IThemeAssociation>>> s = eclipseThemeIds.entrySet();
    //			for (Entry<String, List<IThemeAssociation>> entry : s) {
    //				for (IThemeAssociation themeAssociation : entry.getValue()) {
    //					if (themeId.equals(themeAssociation.getThemeId())) {
    //						return eclipseThemeId.equals(themeAssociation.getEclipseThemeId());
    //					}
    //				}
    //			}
    //		}
    return true;
  }
  List<IThemeAssociation> getThemeAssociations() {
    if(allAssociations.isEmpty()) {
      if(defaultAssociation!=null) {
        allAssociations.add(defaultAssociation);
      }
      final var associations=eclipseThemeIds.values();
      for(final var collection:associations) {
        allAssociations.addAll(collection);
      }
    }
    return allAssociations;
  }
}

package org.eclipse.tm4e.registry;

import java.io.IOException;
import java.io.InputStream;

public interface ITMResource extends ITMDefinition{
  String getPath();
  InputStream getInputStream() throws IOException;
}

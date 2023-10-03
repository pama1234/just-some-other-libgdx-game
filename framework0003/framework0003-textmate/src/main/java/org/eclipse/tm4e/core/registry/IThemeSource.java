package org.eclipse.tm4e.core.registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jdt.annotation.Nullable;

public interface IThemeSource{
  enum ContentType{
    JSON,
    YAML,
    XML
  }
  private static ContentType guessFileFormat(final String fileName) {
    final String extension=fileName.substring(fileName.lastIndexOf('.')+1).trim().toLowerCase();
    return switch(extension) {
      case "json"->ContentType.JSON;
      case "yaml","yaml-tmtheme","yml"->ContentType.YAML;
      case "plist","tmtheme","xml"->ContentType.XML;
      default->throw new IllegalArgumentException("Unsupported file type: "+fileName);
    };
  }
  static IThemeSource fromFile(final Path file) {
    return fromFile(file,null,null);
  }
  static IThemeSource fromFile(final Path file,@Nullable final ContentType contentType,@Nullable final Charset charset) {
    final var filePath=file.toString();
    final var contentType1=contentType==null?guessFileFormat(filePath):contentType;
    return new IThemeSource() {
      @Override
      public Reader getReader() throws IOException {
        return Files.newBufferedReader(file,charset==null?StandardCharsets.UTF_8:charset);
      }
      @Override
      public String getFilePath() {
        return filePath;
      }
      @Override
      public ContentType getContentType() {
        return contentType1;
      }
    };
  }
  static IThemeSource fromResource(final Class<?> clazz,final String resourceName) {
    return fromResource(clazz,resourceName,null,null);
  }
  static IThemeSource fromResource(final Class<?> clazz,final String resourceName,@Nullable final ContentType contentType,
    @Nullable final Charset charset) {
    final var contentType1=contentType==null?guessFileFormat(resourceName):contentType;
    return new IThemeSource() {
      @Override
      public Reader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(
          clazz.getResourceAsStream(resourceName),
          charset==null?StandardCharsets.UTF_8:charset));
      }
      @Override
      public String getFilePath() {
        return resourceName;
      }
      @Override
      public ContentType getContentType() {
        return contentType1;
      }
    };
  }
  static IThemeSource fromString(final ContentType contentType,final String content) {
    return new IThemeSource() {
      @Override
      public Reader getReader() throws IOException {
        return new StringReader(content);
      }
      @Override
      public String getFilePath() {
        return "string."+contentType.name().toLowerCase();
      }
      @Override
      public ContentType getContentType() {
        return contentType;
      }
    };
  }
  default ContentType getContentType() {
    return guessFileFormat(getFilePath());
  }
  String getFilePath();
  Reader getReader() throws IOException;
}

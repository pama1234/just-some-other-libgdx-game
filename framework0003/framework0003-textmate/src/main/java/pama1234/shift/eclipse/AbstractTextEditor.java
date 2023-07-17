package pama1234.shift.eclipse;

// import org.eclipse.ui.IMemento;
// import org.eclipse.ui.texteditor.rulers.RulerColumnPreferenceAdapter;
public class AbstractTextEditor{
  /**
   * Tag used in xml configuration files to specify editor action contributions. Current value:
   * <code>editorContribution</code>
   * 
   * @since 2.0
   */
  public static final String TAG_CONTRIBUTION_TYPE="editorContribution"; //$NON-NLS-1$
  /**
   * Tag used in the {@link IMemento} when saving and restoring the editor's selection offset.
   *
   * @see #saveState(IMemento)
   * @see #restoreState(IMemento)
   * @see #doRestoreState(IMemento)
   * @since 3.3
   */
  public static final String TAG_SELECTION_OFFSET="selectionOffset"; //$NON-NLS-1$
  /**
   * Tag used in the {@link IMemento} when saving and restoring the editor's selection length.
   *
   * @see #saveState(IMemento)
   * @see #restoreState(IMemento)
   * @see #doRestoreState(IMemento)
   * @since 3.3
   */
  public static final String TAG_SELECTION_LENGTH="selectionLength"; //$NON-NLS-1$
  /**
   * Tag used in the {@link IMemento} when saving and restoring the editor's top pixel value.
   *
   * @see #saveState(IMemento)
   * @see #restoreState(IMemento)
   * @see #doRestoreState(IMemento)
   * @since 3.6
   */
  public static final String TAG_SELECTION_TOP_PIXEL="selectionTopPixel"; //$NON-NLS-1$
  /**
   * Tag used in the {@link IMemento} when saving and restoring the editor's horizontal pixel
   * value.
   *
   * @see #saveState(IMemento)
   * @see #restoreState(IMemento)
   * @see #doRestoreState(IMemento)
   * @since 3.6
   */
  public static final String TAG_SELECTION_HORIZONTAL_PIXEL="selectionHorizontalPixel"; //$NON-NLS-1$
  /**
   * The caret width for the wide (double) caret. See
   * https://bugs.eclipse.org/bugs/show_bug.cgi?id=21715. Value: {@value}
   * 
   * @since 3.0
   */
  public static final int WIDE_CARET_WIDTH=2;
  /**
   * The caret width for the narrow (single) caret. See
   * https://bugs.eclipse.org/bugs/show_bug.cgi?id=21715. Value: {@value}
   * 
   * @since 3.0
   */
  public static final int SINGLE_CARET_WIDTH=1;
  /**
   * The symbolic name of the block selection mode font.
   *
   * @since 3.5
   */
  public static final String BLOCK_SELECTION_MODE_FONT="org.eclipse.ui.workbench.texteditor.blockSelectionModeFont"; //$NON-NLS-1$
  /**
   * Key used to look up foreground color preference. Value:
   * <code>AbstractTextEditor.Color.Foreground</code>
   * 
   * @since 2.0
   */
  public static final String PREFERENCE_COLOR_FOREGROUND="AbstractTextEditor.Color.Foreground"; //$NON-NLS-1$
  /**
   * Key used to look up background color preference. Value:
   * <code>AbstractTextEditor.Color.Background</code>
   * 
   * @since 2.0
   */
  public static final String PREFERENCE_COLOR_BACKGROUND="AbstractTextEditor.Color.Background"; //$NON-NLS-1$
  /**
   * Key used to look up foreground color system default preference. Value:
   * <code>AbstractTextEditor.Color.Foreground.SystemDefault</code>
   * 
   * @since 2.0
   */
  public static final String PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT="AbstractTextEditor.Color.Foreground.SystemDefault"; //$NON-NLS-1$
  /**
   * Key used to look up background color system default preference. Value:
   * <code>AbstractTextEditor.Color.Background.SystemDefault</code>
   * 
   * @since 2.0
   */
  public static final String PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT="AbstractTextEditor.Color.Background.SystemDefault"; //$NON-NLS-1$
  /**
   * Key used to look up selection foreground color preference. Value:
   * <code>AbstractTextEditor.Color.SelectionForeground</code>
   * 
   * @since 3.0
   */
  public static final String PREFERENCE_COLOR_SELECTION_FOREGROUND="AbstractTextEditor.Color.SelectionForeground"; //$NON-NLS-1$
  /**
   * Key used to look up selection background color preference. Value:
   * <code>AbstractTextEditor.Color.SelectionBackground</code>
   * 
   * @since 3.0
   */
  public static final String PREFERENCE_COLOR_SELECTION_BACKGROUND="AbstractTextEditor.Color.SelectionBackground"; //$NON-NLS-1$
  /**
   * Key used to look up selection foreground color system default preference. Value:
   * <code>AbstractTextEditor.Color.SelectionForeground.SystemDefault</code>
   * 
   * @since 3.0
   */
  public static final String PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT="AbstractTextEditor.Color.SelectionForeground.SystemDefault"; //$NON-NLS-1$
  /**
   * Key used to look up selection background color system default preference. Value:
   * <code>AbstractTextEditor.Color.SelectionBackground.SystemDefault</code>
   * 
   * @since 3.0
   */
  public static final String PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT="AbstractTextEditor.Color.SelectionBackground.SystemDefault"; //$NON-NLS-1$
  /**
   * Key used to look up find scope background color preference. Value:
   * <code>AbstractTextEditor.Color.FindScope</code>
   * 
   * @since 2.0
   */
  public static final String PREFERENCE_COLOR_FIND_SCOPE="AbstractTextEditor.Color.FindScope"; //$NON-NLS-1$
  /**
   * Key used to look up smart home/end preference. Value:
   * <code>AbstractTextEditor.Navigation.SmartHomeEnd</code>
   * 
   * @since 2.1
   */
  public static final String PREFERENCE_NAVIGATION_SMART_HOME_END="AbstractTextEditor.Navigation.SmartHomeEnd"; //$NON-NLS-1$
  /**
   * Key used to look up the custom caret preference. Value: {@value}
   * 
   * @since 3.0
   */
  public static final String PREFERENCE_USE_CUSTOM_CARETS="AbstractTextEditor.Accessibility.UseCustomCarets"; //$NON-NLS-1$
  /**
   * Key used to look up the caret width preference. Value: {@value}
   * 
   * @since 3.0
   */
  public static final String PREFERENCE_WIDE_CARET="AbstractTextEditor.Accessibility.WideCaret"; //$NON-NLS-1$
  /**
   * A named preference that controls if hyperlinks are turned on or off.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.1
   */
  public static final String PREFERENCE_HYPERLINKS_ENABLED="hyperlinksEnabled"; //$NON-NLS-1$
  /**
   * A named preference that controls the key modifier for hyperlinks.
   * <p>
   * Value is of type <code>String</code>.
   * </p>
   *
   * @since 3.1
   */
  public static final String PREFERENCE_HYPERLINK_KEY_MODIFIER="hyperlinkKeyModifier"; //$NON-NLS-1$
  /**
   * A named preference that controls the key modifier mask for hyperlinks. The value is only used
   * if the value of <code>PREFERENCE_HYPERLINK_KEY_MODIFIER</code> cannot be resolved to valid
   * SWT modifier bits.
   * <p>
   * Value is of type <code>String</code>.
   * </p>
   *
   * @see #PREFERENCE_HYPERLINK_KEY_MODIFIER
   * @since 3.1
   */
  public static final String PREFERENCE_HYPERLINK_KEY_MODIFIER_MASK="hyperlinkKeyModifierMask"; //$NON-NLS-1$
  /**
   * A named preference that controls the visible ruler column contributions.
   * <p>
   * Value is of type <code>String</code> and should be read using a
   * {@link RulerColumnPreferenceAdapter}.
   * </p>
   *
   * @since 3.3
   */
  public static final String PREFERENCE_RULER_CONTRIBUTIONS="rulerContributions"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of whitespace characters.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * <p>
   * The following preferences can be used for fine-grained configuration when enabled.
   * </p>
   * <ul>
   * <li>{@link *#PREFERENCE_SHOW_LEADING_SPACES}</li>
   * <li>{@link *#PREFERENCE_SHOW_ENCLOSED_SPACES}</li>
   * <li>{@link *#PREFERENCE_SHOW_TRAILING_SPACES}</li>
   * <li>{@link *#PREFERENCE_SHOW_LEADING_IDEOGRAPHIC_SPACES}</li>
   * <li>{@link *#PREFERENCE_SHOW_ENCLOSED_IDEOGRAPHIC_SPACES}</li>
   * <li>{@link *#PREFERENCE_SHOW_TRAILING_IDEOGRAPHIC_SPACES}</li>
   * <li>{@link *#PREFERENCE_SHOW_LEADING_TABS}</li>
   * <li>{@link *#PREFERENCE_SHOW_ENCLOSED_TABS}</li>
   * <li>{@link *#PREFERENCE_SHOW_TRAILING_TABS}</li>
   * <li>{@link *#PREFERENCE_SHOW_CARRIAGE_RETURN}</li>
   * <li>{@link *#PREFERENCE_SHOW_LINE_FEED}</li>
   * <li>{@link *#PREFERENCE_WHITESPACE_CHARACTER_ALPHA_VALUE}</li>
   * </ul>
   *
   * @since 3.3
   */
  public static final String PREFERENCE_SHOW_WHITESPACE_CHARACTERS="showWhitespaceCharacters"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of leading Space characters. The value is used
   * only if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is <code>true</code>.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_SHOW_LEADING_SPACES="showLeadingSpaces"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of enclosed Space characters. The value is used
   * only if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is <code>true</code>.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_SHOW_ENCLOSED_SPACES="showEnclosedSpaces"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of trailing Space characters. The value is used
   * only if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is <code>true</code>.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_SHOW_TRAILING_SPACES="showTrailingSpaces"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of leading Ideographic Space characters. The
   * value is used only if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is
   * <code>true</code>.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_SHOW_LEADING_IDEOGRAPHIC_SPACES="showLeadingIdeographicSpaces"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of enclosed Ideographic Space characters. The
   * value is used only if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is
   * <code>true</code>.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_SHOW_ENCLOSED_IDEOGRAPHIC_SPACES="showEnclosedIdeographicSpaces"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of trailing Ideographic Space characters. The
   * value is used only if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is
   * <code>true</code>.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_SHOW_TRAILING_IDEOGRAPHIC_SPACES="showTrailingIdeographicSpaces"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of leading Tab characters. The value is used
   * only if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is <code>true</code>.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_SHOW_LEADING_TABS="showLeadingTabs"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of enclosed Tab characters. The value is used
   * only if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is <code>true</code>.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_SHOW_ENCLOSED_TABS="showEnclosedTabs"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of trailing Tab characters. The value is used
   * only if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is <code>true</code>.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_SHOW_TRAILING_TABS="showTrailingTabs"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of Carriage Return characters. The value is used
   * only if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is <code>true</code>.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_SHOW_CARRIAGE_RETURN="showCarriageReturn"; //$NON-NLS-1$
  /**
   * A named preference that controls the display of Line Feed characters. The value is used only
   * if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is <code>true</code>.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_SHOW_LINE_FEED="showLineFeed"; //$NON-NLS-1$
  /**
   * A named preference that controls the alpha value of whitespace characters. The value is used
   * only if the value of {@link *#PREFERENCE_SHOW_WHITESPACE_CHARACTERS} is <code>true</code>.
   * <p>
   * Value is of type <code>Integer</code>.
   * </p>
   *
   * @since 3.7
   */
  public static final String PREFERENCE_WHITESPACE_CHARACTER_ALPHA_VALUE="whitespaceCharacterAlphaValue"; //$NON-NLS-1$
  /**
   * A named preference that controls whether text drag and drop is enabled.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.3
   */
  public static final String PREFERENCE_TEXT_DRAG_AND_DROP_ENABLED="textDragAndDropEnabled"; //$NON-NLS-1$
  /**
   * A named preference that controls if hovers should automatically be closed when the mouse is
   * moved into them, or when they should be enriched.
   * <p>
   * Value is of type <code>Integer</code> and maps to the following
   * {@link org.eclipse.jface.text.ITextViewerExtension8.EnrichMode}:
   * </p>
   * <ul>
   * <li>-1: <code>null</code> (don't allow moving the mouse into a hover),</li>
   * <li>0: {@link org.eclipse.jface.text.ITextViewerExtension8.EnrichMode#AFTER_DELAY},</li>
   * <li>1: {@link org.eclipse.jface.text.ITextViewerExtension8.EnrichMode#IMMEDIATELY},</li>
   * <li>2: {@link org.eclipse.jface.text.ITextViewerExtension8.EnrichMode#ON_CLICK}.</li>
   * </ul>
   *
   * @since 3.4
   */
  public static final String PREFERENCE_HOVER_ENRICH_MODE="hoverReplaceMode"; //$NON-NLS-1$
  /**
   * A named preference to control the initial word wrap status.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.10
   */
  public static final String PREFERENCE_WORD_WRAP_ENABLED="wordwrap.enabled"; //$NON-NLS-1$
  /**
   * A named preference to control the initial caret offset visibility on the status line.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.13
   */
  public static final String PREFERENCE_SHOW_CARET_OFFSET="showCaretOffset"; //$NON-NLS-1$
  /**
   * A named preference to control the selection visibility on the status line.
   * <p>
   * Value is of type <code>Boolean</code>.
   * </p>
   *
   * @since 3.13
   */
  public static final String PREFERENCE_SHOW_SELECTION_SIZE="showSelectionSize"; //$NON-NLS-1$
  /** Menu id for the editor context menu. */
  public static final String DEFAULT_EDITOR_CONTEXT_MENU_ID="#EditorContext"; //$NON-NLS-1$
  /** Menu id for the ruler context menu. */
  public static final String DEFAULT_RULER_CONTEXT_MENU_ID="#RulerContext"; //$NON-NLS-1$
  /**
   * Menu id used to contribute to the editor context menu of all textual editors.
   *
   * @since 3.5
   */
  public static final String COMMON_EDITOR_CONTEXT_MENU_ID="#AbstractTextEditorContext"; //$NON-NLS-1$
  /**
   * Menu id used to contribute to the ruler context menu of all textual editors.
   *
   * @since 3.5
   */
  public static final String COMMON_RULER_CONTEXT_MENU_ID="#AbstractTextEditorRulerContext"; //$NON-NLS-1$
  /** The width of the vertical ruler. */
  protected static final int VERTICAL_RULER_WIDTH=12;
  /**
   * The complete mapping between action definition IDs used by eclipse and StyledText actions.
   *
   * @since 2.0
   */
}

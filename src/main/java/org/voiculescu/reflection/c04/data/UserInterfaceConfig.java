package org.voiculescu.reflection.c04.data;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserInterfaceConfig {
    private String titleColor;
    private String titleText;
    private short[] titleTextSizes;
    private String[] titleFonts;
    private short titleFontSize;
    private short footerFontSize;

}

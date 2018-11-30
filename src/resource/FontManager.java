package resource;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;

/*
 * Classe FontManager (Singleton)
 * 
 * 	Classe respons�vel por manipular as inst�ncias da classe Font no programa,
 * havendo uma estrutura de mapa, onde h� uma inst�ncia de Font para uma chave
 * de valor inteiro, representando um tamanho de fonte.
 * 
 *  Essa classe l� um arquivo padr�o de fonte, utilizado no programa inteiro, 
 * e atrav�s do m�todo getFont(int), checa se h� alguma chave com o valor passado
 * como par�metro no mapa. Caso n�o exista, instancia a fonte, e a armazena na estrutura
 * com o valor como chave. Caso contr�rio retorna o valor j� armazenado. Por exemplo:
 * 
 * 		Font font1 = FontManager.getInstance().getFont(12) // Instancia uma nova fonte e armazena no mapa
 * 		Font font2 = FontManager.getInstance().getFont(12) // N�o instancia, retorna a inst�ncia j� criada
 * 		Font font3 = FontManager.getInstance().getFont(14) // Instancia uma nova fonte
 * 
 * 	FontManager � um singleton, ou seja, s� h� uma inst�ncia dela em todo o programa,
 * sendo assim, para utiliz�-la, deve-se chamar o m�todo est�tico getInstance()
 */
public class FontManager {
	public static final String FONT_RESOURCE_FILENAME = "pixeled.ttf";
	public static final int    DEFAULT_FONT_SIZE 	  = 16;
	
	private static FontManager instance;
	
	private HashMap<Integer, Font> fonts;
	
	private FontManager() {}
	
	public void initAllFonts() {
		fonts = new HashMap<>();
		
		this.loadFont(FONT_RESOURCE_FILENAME, 14, Font.PLAIN);
		this.loadFont(FONT_RESOURCE_FILENAME, 16, Font.PLAIN);
		this.loadFont(FONT_RESOURCE_FILENAME, 18, Font.PLAIN);
	}
	
	public Font getFont(int size) {
		Font font = this.fonts.get(size);
		
		if (font == null)
			font = loadFont(FONT_RESOURCE_FILENAME, size, Font.PLAIN);
		
		return font;
	}
	
	private Font loadFont(String fontFilename, int size, int style) {
		Font loadedFont = null;
		
		try {
			loadedFont = Font.createFont(Font.TRUETYPE_FONT, Resource.loadResource(fontFilename))
					.deriveFont(style, size);
			
		} catch (FontFormatException | IOException e) {
			System.err.printf("Error loading font %s", fontFilename);
			e.printStackTrace();
		}
		
		if (loadedFont == null)
			loadedFont = new Font(Font.SANS_SERIF, style, size);
		
		this.fonts.put(size, loadedFont);
		
		return loadedFont;
	}
	
	public static FontManager getInstance() {
		if (FontManager.instance == null)
			FontManager.instance = new FontManager();
		
		return FontManager.instance;
	}
}

package core.lab_3;

public class TranslationPair {
    private final String original;
    private final String translation;

    public TranslationPair(String original, String translation) {
        this.original = original;
        this.translation = translation;
    }

    public String getOriginal() { return original; }
    public String getTranslation() { return translation; }
}
package core.lab_3;

public class TextTranslator {
    private final Dictionary dictionary;

    public TextTranslator(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String translate(String text) {
        StringBuilder result = new StringBuilder();
        String lowerText = text.toLowerCase();
        int i = 0;

        while (i < text.length()) {
            if (Character.isWhitespace(text.charAt(i)) ||
                    !Character.isLetterOrDigit(text.charAt(i))) {
                result.append(text.charAt(i));
                i++;
                continue;
            }

            boolean found = false;
            for (TranslationPair pair : dictionary.entries()) {
                String original = pair.getOriginal();
                int endIndex = i + original.length();

                if (endIndex <= lowerText.length() &&
                        lowerText.startsWith(original, i) &&
                        !isWordPart(lowerText, endIndex)) {

                    String translated = applyCaseRules(
                            text.substring(i, i + original.length()),
                            pair.getTranslation()
                    );
                    result.append(translated);
                    i += original.length();
                    found = true;
                    break;
                }
            }

            if (!found) {
                int wordEnd = i;
                while (wordEnd < text.length() &&
                        Character.isLetterOrDigit(text.charAt(wordEnd))) {
                    wordEnd++;
                }
                result.append(text.substring(i, wordEnd));
                i = wordEnd;
            }
        }

        return result.toString();
    }

    private boolean isWordPart(String text, int position) {
        return position < text.length() &&
                Character.isLetterOrDigit(text.charAt(position));
    }

    private String applyCaseRules(String original, String translation) {
        if (original.isEmpty()) return translation;

        if (original.equals(original.toUpperCase())) {
            return translation.toUpperCase();
        }

        if (Character.isUpperCase(original.charAt(0))) {
            return Character.toUpperCase(translation.charAt(0)) +
                    translation.substring(1).toLowerCase();
        }

        return translation;
    }
}
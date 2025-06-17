import javax.swing.JOptionPane;
import java.util.StringTokenizer;

public class Calculadora {
    public static void main(String[] args) {
        showWelcomeDialog();

        boolean shouldContinue = true;

        while (shouldContinue) {
            String input = requestOperation();

            try {
                double result = evaluateOperation(extractOperandsAndOperator(input));
                showResult(result);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            shouldContinue = shouldContinuePrompt();
        }

        showGoodbyeDialog();
    }

    // Mostrar mensaje de bienvenida al iniciar el programa
    private static void showWelcomeDialog() {
        JOptionPane.showMessageDialog(
                null,
                "Bienvenido a la calculadora simple",
                "Calculadora",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Pedir al usuario que ingrese la operación
    private static String requestOperation() {
        return JOptionPane.showInputDialog(
                null,
                "Ingrese la operación en el formato \"número operador número\"\n" +
                        "Por ejemplo: 2 + 3",
                "Operación",
                JOptionPane.QUESTION_MESSAGE
        );
    }

    // Preguntar al usuario si desea realizar otra operación
    private static boolean shouldContinuePrompt() {
        int response = JOptionPane.showConfirmDialog(
                null,
                "¿Desea realizar otra operación?",
                "Continuar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return response == JOptionPane.YES_OPTION;
    }

    // Mostrar mensaje de despedida al salir
    private static void showGoodbyeDialog() {
        JOptionPane.showMessageDialog(
                null,
                "Gracias por usar la calculadora.",
                "Adiós",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Validar y extraer operandos y operador
    private static Object[] extractOperandsAndOperator(String input) {
        StringTokenizer tokenizer = new StringTokenizer(input);
        if (tokenizer.countTokens() != 3) {
            throw new IllegalArgumentException("Formato inválido. Debe ser: número operador número");
        }

        String operand1Str = tokenizer.nextToken();
        String operatorStr = tokenizer.nextToken();
        String operand2Str = tokenizer.nextToken();

        double operand1;
        double operand2;

        try {
            operand1 = Double.parseDouble(operand1Str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: \"" + operand1Str + "\" no es un número válido.");
        }

        try {
            operand2 = Double.parseDouble(operand2Str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: \"" + operand2Str + "\" no es un número válido.");
        }

        return new Object[] { operand1, operatorStr, operand2 };
    }

    // Realizar el cálculo a partir de los valores extraídos
    private static double evaluateOperation(Object[] extractedData) {
        double operand1 = (double) extractedData[0];
        String operatorStr = (String) extractedData[1];
        double operand2 = (double) extractedData[2];

        double result;
        switch (operatorStr) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                if (operand2 == 0) {
                    throw new IllegalArgumentException("Error: división por cero.");
                }
                result = operand1 / operand2;
                break;
            default:
                throw new IllegalArgumentException("Operador \"" + operatorStr + "\" no válido. Use +, -, * o /.");
        }
        return result;
    }

    // Mostrar el resultado al usuario
    private static void showResult(double result) {
        String display;

        if (result == Math.floor(result)) {
            display = Long.toString((long) result);
        } else {
            display = Double.toString(result);
        }

        JOptionPane.showMessageDialog(
                null,
                "Resultado: " + display,
                "Resultado",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}
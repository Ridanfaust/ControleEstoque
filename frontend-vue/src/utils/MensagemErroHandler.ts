export default class ErrorHandler {
  /**
   * Extract error messages from API response
   * @param error - The error object from catch block
   * @param defaultMessage - Default message if no specific error is found
   * @returns Formatted error message string
   */
  static msgErro(error: any, defaultMessage = "Ocorreu um erro na operação."): string {
    let errorMessage = "";

    // Handle validation errors (fields with specific messages)
    if (error.response?.data?.errors) {
      for (const field in error.response.data.errors) {
        errorMessage += `${error.response.data.errors[field]}\n`;
      }
    }

    // If no specific errors were found, use the general message
    if (!errorMessage.trim()) {
      errorMessage = error.response?.data?.message || defaultMessage;
    }

    return errorMessage.trim();
  }
}

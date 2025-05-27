export function formatCurrency(value: number | null | undefined, locale: string = "pt-BR", currency: string = "BRL"): string {
  if (value === null || value === undefined || isNaN(value)) {
    return "-";
  }
  return new Intl.NumberFormat(locale, {
    style: "currency",
    currency,
  }).format(value);
}

export function formatCurrencyInput(value: string): string {
  const numericValue = value.replace(/\D/g, "");
  const valueInCents = parseInt(numericValue) || 0;
  const valueAsDecimal = valueInCents / 100;

  return new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
    minimumFractionDigits: 2,
  }).format(valueAsDecimal);
}

export function parseCurrency(value: string): number {
  const cleanValue = value.replace(/[^\d,]/g, "").replace(",", ".");
  return parseFloat(cleanValue) || 0;
}

import { reactive } from "vue"

export interface AlertState {
  show: boolean
  variant: "success" | "error" | "warning" | "info"
  title: string
  message: string
}

export const alertState = reactive<AlertState>({
  show: false,
  variant: "success",
  title: "",
  message: "",
})

export const useAlertStore = () => {
  const showAlert = (variant: AlertState["variant"], title: string, message: string) => {
    alertState.show = true
    alertState.variant = variant
    alertState.title = title
    alertState.message = message
  }

  const showSuccess = (message: string) => {
    alertState.show = true
    alertState.variant = "success"
    alertState.title = "Sucesso"
    alertState.message = message
  }

  const showError = (message: string) => {
    alertState.show = true
    alertState.variant = "error"
    alertState.title = "Erro"
    alertState.message = message
  }

  const hideAlert = () => {
    alertState.show = false
    alertState.title = ""
    alertState.message = ""
  }

  return {
    alertState,
    showAlert,
    hideAlert,
    showSuccess,
    showError,
  }
}

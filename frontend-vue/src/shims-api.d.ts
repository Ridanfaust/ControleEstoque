declare module "@/services/api.js" {
  const api: {
    baseUrl: string
    get: (url: string, config?: any) => Promise<any>
    post: (url: string, data?: any, config?: any) => Promise<any>
    put: (url: string, data?: any, config?: any) => Promise<any>
    delete: (url: string, config?: any) => Promise<any>
  }
  export default api
}

import Cookies from 'js-cookie'
const TOKEN = 'DKUSERTOKEN'
export function setToken(val) {
    return Cookies.set(TOKEN,val)
}
export function getToken() {
    return Cookies.get(TOKEN)
}
export function removeToken() {
    return Cookies.remove(TOKEN)
}
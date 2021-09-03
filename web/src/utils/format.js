export function formatTime(time) {
    let date = new Date(time)
    date = `${date.getFullYear()}-(${date.getMonth()+1})-${date.getDay()}`
    return date
}
export function phoneLength(val) {
    if(val && typeof val === "string")
    {
        val = val.substring(0,11)
        return val
    }
}
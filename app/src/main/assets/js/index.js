document.getElementById('test1').innerHTML = 'tetetete'

function showAndroidToast(toast) {
    console.info(JSON.stringify(window.navigator))
    Android.showToast(toast)
}

function changeText() {
    const a = Math.floor(Math.random() * 100)
    Android.changeFloatText(a + ' had changed?')
}

function changeTextView(text) {
    document.getElementById('test1').innerHTML = text
    return text
}


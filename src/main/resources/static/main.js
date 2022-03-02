function generateMnemonic(){
    const Http = new XMLHttpRequest();
    Http.open("GET", 'http://127.0.0.1:8080/generate');
    Http.send();

    Http.onreadystatechange = (e) => {
        document.getElementById("mnemonic").innerText=Http.responseText;
    }
}
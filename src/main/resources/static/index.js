async function wordShuffle (){
    const field = document.getElementById("word-field")
    field.innerHTML=''
    const  response = await fetch("http://localhost:9090/cards");
    const cardsJson = await response.json();
    console.log(cardsJson)
    console.log(typeof (cardsJson))

    cardsJson.forEach((item)=>{
        const wordCard = document.createElement('div');
        wordCard.className='word';
        wordCard.innerText= item.word;
        wordCard.dataset.color= item.cardType;
        field.appendChild(wordCard)
    })
}
function userShuffle(){
    console.log("shuffle users");
}

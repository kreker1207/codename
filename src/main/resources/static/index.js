window.onload = function(){
    let cookie = document.cookie
    console.log(cookie)
    if(cookie == null || cookie ==="") {
        let person = prompt("Please enter your username:", "user");
        if (person == null || person === "") {
            document.cookie = "username=user";
        } else {
            document.cookie = "username="+person;
        }
    }
    connect();
}
let stompClient = null;

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (generateCards) {
            wordShuffle(JSON.parse(generateCards.body));
        });

    });
}

function wordShuffle(response) {
    const field = document.getElementById("word-field");
    field.innerHTML = '';

    let orange = 0;
    let blue = 0;

    response.forEach((item) => {
        const wordCard = document.createElement('div');
        wordCard.className = 'word';
        wordCard.innerText = item.word;
        wordCard.dataset.color = item.cardType;
        field.appendChild(wordCard);
        item.cardType == 'BLUE' ? blue++ : item.cardType == 'ORANGE' ? orange++ : 0;
    });
    cardNum('orange', orange);
    cardNum('blue', blue);
}

function cardNum(type, num){
    console.log(type + " " + num)
    const side = document.getElementById("n"+type)
    side.innerText='';
    side.innerText = num
}
function generateCards() {
    stompClient.send('/app/cards', {}, '');
}
import React, {useState} from "react";
import {Button, Card, ListGroup} from "react-bootstrap";

export function Karta() {
    const [text, setText] = useState("nic tu nie ma")

    function addText() {
        var xhr = new XMLHttpRequest()
        xhr.addEventListener('load', () => {
            setText(xhr.responseText)
        });
        xhr.open('GET', 'http://desolate-waters-27530.herokuapp.com/greeting');
        xhr.send();
    }

    function clearText() {
        setText("spierdalaj")
    }

    return (
        <div>
            <Card style={{ width: '20rem', height: '20rem' }}>
                <Card.Body>
                    <Card.Title>Miejsce na requesta</Card.Title>
                    <Card.Text>
                        {text}
                    </Card.Text>
                </Card.Body>
                <ListGroup className="list-group-flush">
                    <Button onClick={addText} variant="danger"> Wyślij kurwa requesta </Button>
                    <Button onClick={clearText} variant="success"> Wyczyść tego jebanego requesta </Button>
                </ListGroup>
            </Card>
        </div>
    );
}


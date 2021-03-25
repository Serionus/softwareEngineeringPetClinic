import 'bootstrap/dist/css/bootstrap.min.css';
// import image from "./assets/img.png";
import {Karta} from "./Karta";
import {Pasek} from "./Pasek";
import {
    Form,
    FormControl,
    Nav,
    Navbar,
    NavDropdown,
    Button,
    Row,
    Col,
    Container
} from "react-bootstrap";

import React, {Component} from 'react'




class App extends Component {
    render() {
        return(
            <div className="alert-dark">
                <Pasek variant/>
                <Container>
                    <Row className="mb-5 mt-5 ">
                        <Col>
                            <Karta/>
                        </Col>
                        <Col>
                            <Karta/>
                        </Col>
                        <Col>
                            <Karta/>
                        </Col>
                    </Row>
                    <Row className="mt-5">
                        <Col>
                            <Karta/>
                        </Col>
                        <Col>
                            <Karta/>
                        </Col>
                        <Col>
                            <Karta/>
                        </Col>
                    </Row>
                </Container>
            </div>
        );
    }
}

export default App;

import {Button, Form, FormControl, Nav, Navbar, NavDropdown} from "react-bootstrap";

export function Pasek () {

    return(
        <div>
            <Navbar bg="light" expand="lg">
                <Navbar.Brand href="#home">GÓWNIANA    STRONA     ANTONIEGO</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <NavDropdown title="ROZWIJANKO" id="basic-nav-dropdown">
                            <NavDropdown.Item href="#action/3.1">COŚ</NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.2">INNE COŚ</NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.3">JESZCZE INNE COŚ</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#action/3.4">tu wyślij kurwa requesta</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                    <Form inline>
                        <FormControl type="text" placeholder="wpisz coś kurwa" className="mr-sm-2" />
                        <Button variant="outline-success">Szukaj kurwa!</Button>
                    </Form>
                </Navbar.Collapse>
            </Navbar>
        </div>
    );
}
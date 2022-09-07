import React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import SettingsIcon from '@mui/icons-material/Settings';
import DeleteIcon from '@mui/icons-material/Delete';
import { Row, Col } from 'reactstrap';
import moment from 'moment';

function TableComp(props) {

    return (
        <>
            <TableContainer style={{ overflowY: "scroll", overflowX: "initial", height: 360 }}>
                <Table stickyHeader sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead style={{ backgroundColor: "lightgray" }}>
                        <TableRow>
                            <TableCell align="center" style={{ backgroundColor: "lightgray" }}>
                                <Row>
                                    <Col lg="12">
                                        <h5><b>Status</b></h5>
                                    </Col>
                                    <Col lg="12" style={{ height: 30 }}>
                                    </Col>
                                </Row>
                            </TableCell>
                            <TableCell align="center" style={{ backgroundColor: "lightgray" }}>
                                <Row>
                                    <Col lg="12">
                                        <h5><b>Description</b></h5>
                                    </Col>
                                    <Col lg="12">
                                        <input onChange={(e) => { props.filter(e.target.value); }}/>
                                    </Col>
                                </Row>
                            </TableCell>
                            <TableCell align="center" style={{ backgroundColor: "lightgray" }}>
                                <Row>
                                    <Col lg="12">
                                        <h5><b>Date</b></h5>
                                    </Col>
                                    <Col lg="12" style={{ height: 30 }}>
                                    </Col>
                                </Row>
                            </TableCell>
                            <TableCell align="center" style={{ backgroundColor: "lightgray" }}>
                                <Row>
                                    <Col lg="12">
                                        <h5><b>Modify</b></h5>
                                    </Col>
                                    <Col lg="12" style={{ height: 30 }}>
                                    </Col>
                                </Row>
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody style={{ overflowY: "scroll", height: 350 }}>
                        {props.data.map((row, index) => (
                            <TableRow
                                key={row.id}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell align="center">
                                    <input className="completed_checkbox" type="checkbox" defaultChecked={row.complete} onChange={(e) => {
                                        props.setCompleteData(row, e);
                                        const checkboxArray = document.getElementsByClassName("completed_checkbox")
                                        for (const checkbox of checkboxArray) {
                                            checkbox.disabled = true
                                        }
                                    }} />
                                </TableCell>
                                <TableCell align="center">{row.description}</TableCell>
                                <TableCell align="center">{moment(row.date).format('DD/MM/YYYY')}</TableCell>
                                <TableCell align="center">
                                    <SettingsIcon style={{ cursor: "pointer" }} id={`settings_${index}`} onMouseEnter={() => {
                                        const button = document.getElementById(`settings_${index}`)
                                        button.style.color = "blue"
                                    }}
                                        onMouseLeave={() => {
                                            const button = document.getElementById(`settings_${index}`)
                                            button.style.color = "black"
                                        }}
                                        onClick={() => {
                                            props.setSelectedData(row);
                                            props.setModal(true);
                                        }} />
                                    <DeleteIcon style={{ cursor: "pointer" }} id={`delete_${index}`} onMouseEnter={() => {
                                        const button = document.getElementById(`delete_${index}`)
                                        button.style.color = "red"
                                    }}
                                        onMouseLeave={() => {
                                            const button = document.getElementById(`delete_${index}`)
                                            button.style.color = "black"
                                        }}
                                        onClick={() => {
                                            props.setSelectedData(row);
                                            props.setDeleteModal(true);
                                        }} />
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </>

    )
}

export default TableComp;
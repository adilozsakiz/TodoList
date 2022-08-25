import axios from "axios";
import React, { useEffect, useState } from "react";
import { Card, CardBody, CardHeader, Label, Row, Col, Modal, ModalHeader, ModalBody,FormGroup,Input, ModalFooter, Button } from 'reactstrap';
import SettingsIcon from '@mui/icons-material/Settings';
import DeleteIcon from '@mui/icons-material/Delete';
import moment from "moment";

function App() {

  const [listData, setListData] = useState([])
  const [modal, setModal] = useState(false)
  const [addModal, setAddModal] = useState(false)
  const [deleteModal, setDeleteModal] = useState(false)
  const [selectedData, setSelectedData] = useState()

  async function getAllData() {
    await axios.get('/api/todolist/getAll').then((resp) => {
      setListData(resp.data)
    }).catch((err) => {
      console.error(err)
    })
  }

  async function setData() {
    await axios.put(`/api/todolist/${selectedData.id}`,{
      description: document.getElementById("description_input").value,
      date: moment(document.getElementById("date_input").value).format("YYYY-MM-DD"),
      complete: selectedData.complete
    }).then((resp)=>{
      setSelectedData();
      getAllData();
      setModal(false);
    }).catch((err)=> {
      console.error(err) 
    })
  }

  async function setCompleteData(selectedData,value) {
    await axios.put(`/api/todolist/${selectedData.id}`,{
      description: selectedData.description,
      date: selectedData.date,
      complete: value.target.checked
    }).then((resp)=>{
      setSelectedData();
      getAllData();
      const checkboxArray=document.getElementsByClassName("completed_checkbox")
                      for(const checkbox of checkboxArray) {
                        checkbox.disabled=false
                      }
      setModal(false);
    }).catch((err)=> {
      console.error(err)
    })
  }

  async function deleteData() {
    await axios.delete(`/api/todolist/${selectedData.id}`)
    .then((resp)=>{
      setSelectedData();
      getAllData();
      setDeleteModal(false);
    }).catch((err)=> {
      console.error(err)
    })
  }

  async function addData() {
    await axios.post(`/api/todolist`,{
      description: document.getElementById("description_input").value,
      date: moment(document.getElementById("date_input").value).format("YYYY-MM-DD")
    })
    .then((resp)=>{
      getAllData();
      setAddModal(false);
    }).catch((err)=> {
      console.error(err)
    })
  }


  useEffect(() => {
    getAllData();
  }, [])

  return (
    <div className="d-flex justify-content-center" >
      <Card className="mt-4" style={{ backgroundColor: "lightgray", height: "500px", width: "800px" }}>
        <CardHeader className="d-flex justify-content-center">
          <Col lg="1"></Col>
          <Col className="text-center" lg="10"><Label>
            <h3>ToDo List</h3>
          </Label></Col>
          <Col lg="1">
            <Button onClick={()=>{setAddModal(true)}} size="sm" color="primary">
            New ToDo
          </Button>
          </Col>
        </CardHeader>
        <CardBody>
          <Row className="mb-4">
            <Col lg="2" className="d-flex justify-content-center">
              <h5>Status</h5>
            </Col>
            <Col lg="5">
              <h5>Description</h5>
            </Col>
            <Col lg="3">
              <h5>Date</h5>
            </Col>
            <Col lg="2" className="d-flex justify-content-center">
              <h5>Modify</h5>
            </Col>

          </Row>
          {
            listData.map((item, index) => (
              <Row key={index}>
                <Col lg="2" className="d-flex justify-content-center">
                <input className="completed_checkbox" type="checkbox" defaultChecked={item.complete} onChange={(e) => {setCompleteData(item,e);
                      const checkboxArray=document.getElementsByClassName("completed_checkbox")
                      for(const checkbox of checkboxArray) {
                        checkbox.disabled=true
                      }
                } } /> 
                </Col>
                <Col lg="5">
                  {item.description}
                </Col>
                <Col lg="3">
                  {moment(item.date).format('DD/MM/YYYY')}
                </Col>
                <Col lg="2" className="d-flex justify-content-center"> 
                <SettingsIcon style={{cursor:"pointer"}} id={`settings_${index}`} onMouseEnter={()=> {
                  const button=document.getElementById(`settings_${index}`)
                  button.style.color="blue"
                }}
                onMouseLeave={()=> {
                  const button=document.getElementById(`settings_${index}`)
                  button.style.color="black"
                }}
                onClick={()=> {
                  setSelectedData(item);
                  setModal(true);
                }} />
                <DeleteIcon style={{cursor:"pointer"}} id={`delete_${index}`} onMouseEnter={()=> {
                  const button=document.getElementById(`delete_${index}`)
                  button.style.color="red"
                }}
                onMouseLeave={()=> {
                  const button=document.getElementById(`delete_${index}`)
                  button.style.color="black"
                }}
                onClick={()=> {
                  setSelectedData(item);
                  setDeleteModal(true);
                }} />
                </Col>
              </Row>
            ))
          }
        </CardBody>
      </Card>
      <Modal isOpen={modal} toggle={() => { setModal(!modal) }}>
        <ModalHeader toggle={() => { setModal(!modal) }}>
          Update Data
        </ModalHeader>
        <ModalBody>
          <FormGroup>
            <Label for="description">Description</Label>
            <Input defaultValue={selectedData?.description} id="description_input" />
          </FormGroup>
          <FormGroup>
            <Label for="date">Date</Label>
            <Input defaultValue={selectedData?.date} id="date_input" type="date" />
          </FormGroup>
        </ModalBody>
        <ModalFooter>
          <Button color="success" onClick={()=> { setData() }}>Save</Button>
          <Button color="danger" onClick={()=> { setSelectedData();setModal(false) }}>Cancel</Button>
        </ModalFooter>
      </Modal>
      <Modal isOpen={deleteModal} toggle={() => {setDeleteModal(!deleteModal)}}>
            <ModalHeader>
              Are you sure?
            </ModalHeader>
            <ModalBody>
            <FormGroup>
            <Label for="description">Description</Label>
            <Input defaultValue={selectedData?.description} disabled id="description_input" />
          </FormGroup>
          <FormGroup>
            <Label for="date">Date</Label>
            <Input defaultValue={selectedData?.date} disabled id="date_input" type="date" />
          </FormGroup>
            </ModalBody>
            <ModalFooter>
            <Button color="primary" onClick={()=> { deleteData() }}>Delete</Button>
          <Button color="danger" onClick={()=> { setSelectedData();setDeleteModal(false) }}>Cancel</Button>
            </ModalFooter>
      </Modal>
      <Modal isOpen={addModal} toggle={() => {setAddModal(!addModal)}}>
            <ModalHeader>
              Add
            </ModalHeader>
            <ModalBody>
            <FormGroup>
            <Label for="description">Description</Label>
            <Input placeholder="Enter description" id="description_input" />
          </FormGroup>
          <FormGroup>
            <Label for="date">Date</Label>
            <Input placeholder="Select date" id="date_input" type="date" />
          </FormGroup>
            </ModalBody>
            <ModalFooter>
            <Button color="success" onClick={()=> { addData() }}>Save</Button>
          <Button color="danger" onClick={()=> { setSelectedData();setAddModal(false) }}>Cancel</Button>
            </ModalFooter>
      </Modal>
    </div>
  )
}

export default App;
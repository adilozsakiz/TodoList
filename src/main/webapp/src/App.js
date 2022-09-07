import axios from "axios";
import React, { useEffect, useState } from "react";
import { Card, CardBody, CardHeader, Row, Label, Col, Button } from 'reactstrap';
import moment from "moment";
import Table from './Components/Table';
import CustomModal from './Components/Modal';

function App() {
  const [listData, setListData] = useState([]);
  const [modal, setModal] = useState(false);
  const [addModal, setAddModal] = useState(false);
  const [deleteModal, setDeleteModal] = useState(false);
  const [selectedData, setSelectedData] = useState();
  const [selectedModalType, setSelectedModalType] = useState("inital");
  const [filter, setFilter] = useState("");

  async function getAllData(e) {
    await axios.get(e !== undefined && e.length !== 0 ? `/api/todolist/getAll?description=${e}` : `/api/todolist/getAll`).then((resp) => {
      const trueArr = []
      let dataHolder = []
      for(const singleData of resp.data) {
        if(singleData.complete === true) {
          trueArr.push(singleData);
        } else {
          dataHolder.push(singleData);
        }
      }
      trueArr.sort((a, b) => moment(a.date).toDate() - moment(b.date).toDate())
      dataHolder.sort((a, b) => moment(a.date).toDate() - moment(b.date).toDate())
      dataHolder = dataHolder.concat(trueArr);
      setListData(dataHolder);
    }).catch((err) => {
      console.error(err);
    })
  }

  async function setData() {
    await axios.put(`/api/todolist/${selectedData.id}`, {
      description: document.getElementById("description_input").value,
      date: moment(document.getElementById("date_input").value).format("YYYY-MM-DD"),
      complete: selectedData.complete
    }).then((resp) => {
      setSelectedData();
      getAllData(filter);
      setModal(false);
    }).catch((err) => {
      console.error(err);
    })
  }

  async function setCompleteData(selectedData, value) {
    await axios.put(`/api/todolist/${selectedData.id}`, {
      description: selectedData.description,
      date: selectedData.date,
      complete: value.target.checked
    }).then((resp) => {
      setSelectedData();
      getAllData(filter);
      const checkboxArray = document.getElementsByClassName("completed_checkbox")
      for (const checkbox of checkboxArray) {
        checkbox.disabled = false;
      }
      setModal(false);
    }).catch((err) => {
      console.error(err);
    })
  }

  async function deleteData() {
    await axios.delete(`/api/todolist/${selectedData.id}`)
      .then((resp) => {
        setSelectedData();
        getAllData(filter);
        setDeleteModal(false);
      }).catch((err) => {
        console.error(err);
      })
  }

  async function addData() {
    await axios.post(`/api/todolist`, {
      description: document.getElementById("description_input").value,
      date: moment(document.getElementById("date_input").value).format("YYYY-MM-DD")
    })
      .then((resp) => {
        getAllData(filter);
        setAddModal(false);
      }).catch((err) => {
        console.error(err);
      })
  }

  function getFilteredData(e) {
    getAllData(e);
    setFilter(e);
  }

  function modalToggler(e) {
    setModal(e);
    setSelectedModalType("update");
  }

  function deleteModalToggler(e) {
    setDeleteModal(e);
    setSelectedModalType("delete");
  }

  function modalSetter(type) {
    if (selectedModalType === "add") {
      if (type === "getter") {
        return addModal;
      } else if (type === "setter") {
        return setAddModal;
      } else if (type === "saver") {
        return addData;
      } else if (type === "data") {
        return {
          header: "Add",
          inputsDisabled: false,
          buttonName: "Save",
          buttonColor: "success"
        };
      } else {
        return false;
      }
    } else if (selectedModalType === "update") {
      if (type === "getter") {
        return modal;
      } else if (type === "setter") {
        return setModal;
      } else if (type === "saver") {
        return setData;
      } else if (type === "data") {
        return {
          header: "Update Data",
          inputsDisabled: false,
          buttonName: "Save",
          buttonColor: "success"
        };
      } else {
        return false;
      }
    } else if (selectedModalType === "delete") {
      if (type === "getter") {
        return deleteModal;
      } else if (type === "setter") {
        return setDeleteModal;
      } else if (type === "saver") {
        return deleteData;
      } else if (type === "data") {
        return {
          header: "Are you sure?",
          inputsDisabled: true,
          buttonName: "Delete",
          buttonColor: "primary"
        };
      } else {
        return false;
      }
    } else if(selectedModalType === "inital") {
      return false;
    } else {
      console.error("Hatalı modal tipi seçimi!");
      setModal(false);
      setDeleteModal(false);
      setAddModal(false);
      return false;
    }
  }

  useEffect(() => {
    getAllData();
  }, [])

  return (
    <div className="d-flex justify-content-center" >
      <Card className="mt-4" style={{ backgroundColor: "lightgray", height: "500px", width: "800px" }}>
        <CardHeader>
          <Row className="d-flex justify-content-between">
            <Col lg="2"></Col>
            <Col className="text-center mt-2" lg="8"><Label>
              <h3>ToDo List</h3>
            </Label></Col>
            <Col lg="2" className="d-flex justify-content-end align-items-center">
              <Button onClick={() => { setAddModal(true); setSelectedModalType("add"); }} style={{ maxHeight: 30 }} size="sm" color="primary">
                New ToDo
              </Button>
            </Col>
          </Row>
        </CardHeader>
        <CardBody>
          <Table data={listData} setCompleteData={setCompleteData} setModal={modalToggler} setDeleteModal={deleteModalToggler} setSelectedData={setSelectedData} filter={getFilteredData} />
        </CardBody>
      </Card>
      <CustomModal modalData={modalSetter("data")} modalGet={modalSetter("getter")} modalSet={modalSetter("setter")} selectedData={selectedData} setSelectedData={setSelectedData} save={modalSetter("saver")} />
    </div>
  )
}

export default App;
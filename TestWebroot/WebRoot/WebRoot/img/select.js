var xmlHttp;
var completeDiv;
var inputField;
var nameTable;
var nameTableBody;
var flag=false;

function createXMLHttpRequest() { //����xmlHttpRequest����
   if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
   else if (window.XMLHttpRequest) {
         xmlHttp = new XMLHttpRequest();               
   }
}

function setflag(){  
   flag = true;
}
       
function DisSelect()
{
   if(flag==false)
      document.getElementById("popup").style.display="none";
}

function initVars() {
inputField = document.getElementById("frmchangshang"); //�����keyword      
nameTable = document.getElementById("name_table");     //table��
completeDiv = document.getElementById("popup");          //div��
nameTableBody = document.getElementById("name_table_body");//tbody��
document.getElementById("popup").style.display="block";   //��ʾdiv��
}

function findNames() {  

    initVars();         
    if (inputField.value.length > 0)
    {
      createXMLHttpRequest();
      var url = "servlet/SearchServlet?keyword="+inputField.value;
      xmlHttp.open("GET", url, true);
      xmlHttp.onreadystatechange = callback;///////�ص�������������
      xmlHttp.send(null);
    }else{                 
      clearNames();       
    }
}

function callback() {
if (xmlHttp.readyState == 4)
{
    if (xmlHttp.status == 200)
    {
       try
          {
             var name = xmlHttp.responseXML.getElementsByTagName("name")[0].firstChild.data;

                                }catch(e){
             document.getElementById("popup").style.display="none";
             clearNames();
          }
       setNames(xmlHttp.responseXML.getElementsByTagName("content"));
     }
else if (xmlHttp.status == 204)
         {
           clearNames();
          }
}
}
       
function setNames(the_names) {           
    clearNames();
    var size = the_names.length;    
    setOffsets();
    var row,cell,spans,hrefs;
    for (var i = 0; i < size; i++) {
      
      var e = the_names[i];
      
      var nextNode=e.getElementsByTagName("name")[0].firstChild.data;
      var nextDuty=e.getElementsByTagName("duty")[0].firstChild.data;
      //����tr��td��spanԪ��
      row =document.createElement("tr");
      cell =document.createElement("td");
      hrefs=document.createElement("a");
      //spans=document.createElement("span");
      //����cell����
      cell.onmouseout = function() {this.className='mouseOut'; flag=false;};
      cell.onmouseover = function() {this.className='mouseOver'; flag=true;};
      cell.setAttribute("bgcolor","#FFFAFA");
      cell.setAttribute("border","0");
      cell.setAttribute("height","20");
          //cell.setAttribute("onmouseover","setflag()");
           //cell.onclick = function() { populateName(this); };
      hrefs.onclick = function() { populateName(this); };
      hrefs.setAttribute("href","javascript:;");
         //��nextNode��ӵ�td
      var txtName = document.createTextNode(nextNode);
      var txtDuty = document.createTextNode(nextDuty);
      hrefs.setAttribute("title",nextDuty);
         // cell.appendChild(txtName);
       //װ���������ݵ�spanԪ��
      hrefs.appendChild(txtName);
      cell.appendChild(hrefs);
     row.appendChild(cell);
      nameTableBody.appendChild(row);
     }
}

function setOffsets() {
     var end = inputField.offsetWidth;
     var left = calculateOffsetLeft(inputField);
     var top = calculateOffsetTop(inputField) + inputField.offsetHeight;
     completeDiv.style.border = "black 1px solid";
     completeDiv.style.left = left + "px";
     completeDiv.style.top = top + "px";
     nameTable.style.width=inputField.offsetWidth;
        }
       
function calculateOffsetLeft(field) {
    return calculateOffset(field, "offsetLeft");
   }

   function calculateOffsetTop(field) {
    return calculateOffset(field, "offsetTop");
   }

function calculateOffset(field, attr) {
       var offset = 0;
    while(field) {
     offset += field[attr];
     field = field.offsetParent;
   }
   return offset;
}

function populateName(cell) { //�õ�cell����(�ڵ�)����ı�ֵ
//������ݵ�webҳ�棬cell---->td����
   inputField.value = cell.firstChild.nodeValue;
   clearNames();
}
//����б�����
function clearNames() {
   var ind = nameTableBody.childNodes.length;
   for (var i = ind - 1; i >= 0 ; i--) {
       nameTableBody.removeChild(nameTableBody.childNodes[i]);
}
   completeDiv.style.border = "none";
}
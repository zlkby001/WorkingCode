var xmlHttp;
var completeDiv;
var inputField;
var nameTable;
var nameTableBody;
var flag=false;

function createXMLHttpRequest() { //创建xmlHttpRequest对象
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
inputField = document.getElementById("frmchangshang"); //输入框keyword      
nameTable = document.getElementById("name_table");     //table层
completeDiv = document.getElementById("popup");          //div层
nameTableBody = document.getElementById("name_table_body");//tbody层
document.getElementById("popup").style.display="block";   //显示div层
}

function findNames() {  

    initVars();         
    if (inputField.value.length > 0)
    {
      createXMLHttpRequest();
      var url = "servlet/SearchServlet?keyword="+inputField.value;
      xmlHttp.open("GET", url, true);
      xmlHttp.onreadystatechange = callback;///////回调函数！！！！
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
      //创建tr，td，span元素
      row =document.createElement("tr");
      cell =document.createElement("td");
      hrefs=document.createElement("a");
      //spans=document.createElement("span");
      //设置cell属性
      cell.onmouseout = function() {this.className='mouseOut'; flag=false;};
      cell.onmouseover = function() {this.className='mouseOver'; flag=true;};
      cell.setAttribute("bgcolor","#FFFAFA");
      cell.setAttribute("border","0");
      cell.setAttribute("height","20");
          //cell.setAttribute("onmouseover","setflag()");
           //cell.onclick = function() { populateName(this); };
      hrefs.onclick = function() { populateName(this); };
      hrefs.setAttribute("href","javascript:;");
         //将nextNode添加到td
      var txtName = document.createTextNode(nextNode);
      var txtDuty = document.createTextNode(nextDuty);
      hrefs.setAttribute("title",nextDuty);
         // cell.appendChild(txtName);
       //装载隐藏数据到span元素
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

function populateName(cell) { //得到cell对象(节点)里的文本值
//填充数据到web页面，cell---->td对象
   inputField.value = cell.firstChild.nodeValue;
   clearNames();
}
//清除列表数组
function clearNames() {
   var ind = nameTableBody.childNodes.length;
   for (var i = ind - 1; i >= 0 ; i--) {
       nameTableBody.removeChild(nameTableBody.childNodes[i]);
}
   completeDiv.style.border = "none";
}
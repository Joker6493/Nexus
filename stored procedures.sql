/*delimiter $$
create procedure test()
begin
select * from system_info;
end $$

delimiter ;*/

/*call test()*/


delimiter $$

drop procedure if exists getSkydiveSystemsList $$

create procedure getSkydiveSystemsList(
in status int,
in stock int
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Returns list of assembled skydive systems with selected status at selected stock
---------------------------------------------------------------------------------------------------
*/

begin

set @status = status;
set @stock = stock;

select si.systemid, si.system_code, si.system_model, si.system_sn, si.system_dom, si.manufacturerid as system_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = si.manufacturerid) as system_manufacturer_name,
ci.canopyid, ci.canopy_model, ci.canopy_size, ci.canopy_sn, ci.canopy_dom, ci.canopy_jumps, ci.manufacturerid as canopy_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ci.manufacturerid) as canopy_manufacturer_name,
ri.reserveid, ri.reserve_model, ri.reserve_size, ri.reserve_sn, ri.reserve_dom, ri.reserve_jumps, ri.reserve_packdate, ri.manufacturerid as reserve_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ri.manufacturerid) as reserve_manufacturer_name,
ai.aadid, ai.aad_model, ai.aad_sn, ai.aad_dom, ai.aad_jumps, ai.aad_nextregl, ai.aad_saved, ai.manufacturerid as aad_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ai.manufacturerid) as aad_manufacturer_name
from system_info si
inner join canopy_info ci ON si.canopyid = ci.canopyid and ci.status = si.status and ci.stockid = si.stockid and ci.systemid = si.systemid
inner join reserve_info ri ON si.reserveid = ri.reserveid and ri.status = si.status and ri.stockid = si.stockid and ri.systemid = si.systemid
inner join aad_info ai ON si.aadid = ai.aadid and ai.status = si.status and ai.stockid = si.stockid and ai.systemid = si.systemid
where si.status = @status and si.stockid = @stock;

end $$

drop procedure if exists getContainersList $$

create procedure getContainersList(
in status int,
in stock int
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Returns list of containeers (systems without other elements) with selected status at selected stock
---------------------------------------------------------------------------------------------------
*/

begin

set @status = status;
set @stock = stock;

select si.systemid, si.system_code, si.system_model, si.system_sn, si.system_dom, si.manufacturerid as system_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = si.manufacturerid) as system_manufacturer_name
from system_info si
where si.canopyid = 0 and si.reserveid = 0 and si.aadid = 0 and si.status = @status and si.stockid = @stock;

end $$

drop procedure if exists getCanopyList $$

create procedure getCanopyList(
in status int,
in stock int
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Returns list of canopies separated from containeers with selected status at selected stock
---------------------------------------------------------------------------------------------------
*/

begin

set @status = status;
set @stock = stock;
select ci.canopyid, ci.canopy_model, ci.canopy_size, ci.canopy_sn, ci.canopy_dom, ci.canopy_jumps, ci.manufacturerid as canopy_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ci.manufacturerid) as canopy_manufacturer_name
from canopy_info ci
where ci.systemid = 0 and ci.status = @status and ci.stockid = @stock;

end $$

drop procedure if exists getReserveList $$

create procedure getReserveList(
in status int,
in stock int
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Returns list of reserves separated from containeers with selected status at selected stock
---------------------------------------------------------------------------------------------------
*/

begin

set @status = status;
set @stock = stock;

select ri.reserveid, ri.reserve_model, ri.reserve_size, ri.reserve_sn, ri.reserve_dom, ri.reserve_jumps, ri.reserve_packdate, ri.manufacturerid as reserve_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ri.manufacturerid) as reserve_manufacturer_name
from reserve_info ri
where ri.systemid = 0 and ri.status = @status and ri.stockid = @stock;

end $$

drop procedure if exists getAadList $$

create procedure getAadList(
in status int,
in stock int
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Returns list of aads separated from containeers with selected status at selected stock
---------------------------------------------------------------------------------------------------
*/

begin

set @status = status;
set @stock = stock;

select ai.aadid, ai.aad_model, ai.aad_sn, ai.aad_dom, ai.aad_jumps, ai.aad_nextregl, ai.aad_saved, ai.manufacturerid as aad_manufacturerid, (select manufacturer_name from manufacturer_info mi where mi.manufacturerid = ai.manufacturerid) as aad_manufacturer_name
from aad_info ai
where ai.systemid = 0 and ai.status = @status and ai.stockid = @stock;

end $$

drop procedure if exists getManufacturerList $$

create procedure getManufacturerList(
in status int)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Returns list of manufacturers with selected status
---------------------------------------------------------------------------------------------------
*/

begin

set @status = status;

select mi.manufacturerid, mi.manufacturer_name, mi.manufacturer_country, mi.manufacturer_telephone, mi.manufacturer_email
from manufacturer_info mi
where mi.status = @status;

end $$

drop procedure if exists getStockList $$

create procedure getStockList(
in status int)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Returns list of stocks with selected status
---------------------------------------------------------------------------------------------------
*/

begin

set @status = status;

select sti.stockid, sti.stock_name
from stock_info sti
where sti.status = @status;

end $$

drop function if exists addSkydiveSystem $$

create function addSkydiveSystem(
system_code varchar(6),
system_model varchar(50),
system_sn varchar(20),
system_dom date,
manufacturerid int(6),
canopyid int(6),
reserveid int(6),
aadid int(6),
stockid int(6),
status int(1)
)
returns int(6)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Add new skydive system with the specified parameters 
Returns id of new element, if successfull, 0 otherwise
---------------------------------------------------------------------------------------------------
*/

begin

set @system_code = system_code;
set @system_model = system_model;
set @system_sn = system_sn;
set @system_dom = system_dom;
set @manufacturerid = manufacturerid;
set @canopyid = canopyid;
set @reserveid = reserveid;
set @aadid = aadid;
set @stockid = stockid;
set @status = status;
set @systemid = 0;

insert
into system_info (system_code, manufacturerid, system_model, system_sn, system_dom, canopyid, reserveid, aadid, status, stockid)
values (@system_code, @manufacturerid, @system_model,@system_sn, @system_dom, @canopyid, @reserveid, @aadid, @status, @stockid);

set @systemid = last_insert_id();

return (@systemid);

end $$

drop function if exists addCanopy $$

create function addCanopy(
canopy_model varchar(50),
canopy_size int(3),
canopy_sn varchar(20),
canopy_dom date,
canopy_jumps int(4),
manufacturerid int(6),
systemid int(6),
stockid int(6),
status int(1)
)
returns int(6)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Add new canopy with the specified parameters 
Returns id of new element, if successfull, 0 otherwise
---------------------------------------------------------------------------------------------------
*/

begin

set @canopy_model = canopy_model;
set @canopy_size = canopy_size;
set @canopy_sn = canopy_sn;
set @canopy_dom = canopy_dom;
set @canopy_jumps = canopy_jumps;
set @manufacturerid = manufacturerid;
set @systemid = systemid;
set @stockid = stockid;
set @status = status;
set @canopyid = 0;

insert
into canopy_info (systemid, manufacturerid, canopy_model, canopy_size, canopy_sn, canopy_dom, canopy_jumps, status, stockid)
values (@systemid, @manufacturerid, @canopy_model, @canopy_size, @canopy_sn, @canopy_dom, @canopy_jumps, @status, @stockid);

set @canopyid = last_insert_id();

return (@canopyid);

end $$

drop function if exists addReserve $$

create function addReserve(
reserve_model varchar(50),
reserve_size int(3),
reserve_sn varchar(20),
reserve_dom date,
reserve_jumps int(4),
reserve_packdate date,
manufacturerid int(6),
systemid int(6),
stockid int(6),
status int(1)
)
returns int(6)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Add new reserve with the specified parameters
Returns id of new element, if successfull, 0 otherwise
---------------------------------------------------------------------------------------------------
*/

begin

set @reserve_model = reserve_model;
set @reserve_size = reserve_size;
set @reserve_sn = reserve_sn;
set @reserve_dom = reserve_dom;
set @reserve_jumps = reserve_jumps;
set @reserve_packdate = reserve_packdate;
set @manufacturerid = manufacturerid;
set @systemid = systemid;
set @stockid = stockid;
set @status = status;
set @reserveid = 0;

insert
into reserve_info (systemid, manufacturerid, reserve_model, reserve_size, reserve_sn, reserve_dom, reserve_jumps, reserve_packdate, status, stockid)
values (@systemid, @manufacturerid, @reserve_model, @reserve_size, @reserve_sn, @reserve_dom, @reserve_jumps, @reserve_packdate, @status, @stockid);

set @reserveid = last_insert_id();

return (@reserveid);

end $$

drop function if exists addAAD $$

create function addAAD(
aad_model varchar(50),
aad_sn varchar(20),
aad_dom date,
aad_jumps int(4),
aad_nextregl date,
aad_saved int(4),
manufacturerid int(6),
systemid int(6),
stockid int(6),
status int(1)
)
returns int(6)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Add new aad with the specified parameters
Returns id of new element, if successfull, 0 otherwise
---------------------------------------------------------------------------------------------------
*/

begin

set @aad_model = aad_model;
set @aad_sn = aad_sn;
set @aad_dom = aad_dom;
set @aad_jumps = aad_jumps;
set @aad_nextregl = aad_nextregl;
set @aad_saved = aad_saved;
set @manufacturerid = manufacturerid;
set @systemid = systemid;
set @stockid = stockid;
set @status = status;
set @aadid = 0;

insert
into aad_info (systemid, manufacturerid, aad_model, aad_sn, aad_dom, aad_jumps, aad_nextregl, status, stockid, aad_saved)
values (@systemid, @manufacturerid, @aad_model, @aad_sn, @aad_dom, @aad_jumps, @aad_nextregl, @status, @stockid, @aad_saved);

set @aadid = last_insert_id();

return (@aadid);

end $$

drop function if exists addStock $$

create function addStock(
stock_name varchar(50),
status int(1)
)
returns int(6)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Add new stock with the specified parameters
Returns id of new element, if successfull, 0 otherwise
---------------------------------------------------------------------------------------------------
*/

begin

set @stock_name = stock_name;
set @status = status;
set @stockid = 0;

insert
into stock_info (stock_name, status)
values (@stock_name, @status);

set @stockid = last_insert_id();

return (@stockid);

end $$

drop function if exists addManufacturer $$

create function addManufacturer(
manufacturer_name varchar(100),
manufacturer_country varchar(100),
manufacturer_telephone varchar(12),
manufacturer_email varchar(50),
status int(1)
)
returns int(6)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Add new manufacturer with the specified parameters
Returns id of new element, if successfull, 0 otherwise
---------------------------------------------------------------------------------------------------
*/

begin

set @manufacturer_name = manufacturer_name;
set @manufacturer_country = manufacturer_country;
set @manufacturer_telephone = manufacturer_telephone;
set @manufacturer_email = manufacturer_email;
set @status = status;
set @manufacturerid = 0;

insert
into manufacturer_info (manufacturer_name, manufacturer_country, manufacturer_telephone, manufacturer_email, status)
values (@manufacturer_name, @manufacturer_country, @manufacturer_telephone, @manufacturer_email, @status);

set @manufacturerid = last_insert_id();

return (@manufacturerid);

end $$

drop procedure if exists editSkydiveSystem $$

create procedure editSkydiveSystem(
in systemid int(6),
in system_code varchar(6),
in system_model varchar(50),
in system_sn varchar(20),
in system_dom date,
in manufacturerid int(6),
in canopyid int(6),
in reserveid int(6),
in aadid int(6),
in stockid int(6),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Edit selected skydive system - update parameters of a specific row
---------------------------------------------------------------------------------------------------
*/

begin

set @systemid = systemid;
set @system_code = system_code;
set @system_model = system_model;
set @system_sn = system_sn;
set @system_dom = system_dom;
set @manufacturerid = manufacturerid;
set @canopyid = canopyid;
set @reserveid = reserveid;
set @aadid = aadid;
set @stockid = stockid;
set @status = status;

update system_info si
set
si.system_code = @system_code,
si.system_model = @system_model,
si.system_sn = @system_sn,
si.system_dom = @system_dom,
si.manufacturerid = @manufacturerid,
si.canopyid = @canopyid,
si.reserveid = @reserveid,
si.aadid = @aadid,
si.stockid = @stockid,
si.status = @status
where si.systemid = @systemid;

end $$

drop procedure if exists editCanopy $$

create procedure editCanopy(
in canopyid int(6),
in canopy_model varchar(50),
in canopy_size int(3),
in canopy_sn varchar(20),
in canopy_dom date,
in canopy_jumps int(4),
in manufacturerid int(6),
in systemid int(6),
in stockid int(6),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Edit selected canopy - update parameters of a specific row
---------------------------------------------------------------------------------------------------
*/

begin

set @canopyid = canopyid;
set @canopy_model = canopy_model;
set @canopy_size = canopy_size;
set @canopy_sn = canopy_sn;
set @canopy_dom = canopy_dom;
set @canopy_jumps = canopy_jumps;
set @manufacturerid = manufacturerid;
set @systemid = systemid;
set @stockid = stockid;
set @status = status;

update canopy_info ci
set
ci.canopy_model = @canopy_model,
ci.canopy_size = @canopy_size,
ci.canopy_sn = @canopy_sn,
ci.canopy_dom = @canopy_dom,
ci.canopy_jumps = @canopy_jumps,
ci.manufacturerid = @manufacturerid,
ci.systemid = @systemid,
ci.stockid = @stockid,
ci.status = @status
where ci.canopyid = @canopyid;

end $$

drop procedure if exists editReserve $$

create procedure editReserve(
in reserveid int(6),
in reserve_model varchar(50),
in reserve_size int(3),
in reserve_sn varchar(20),
in reserve_dom date,
in reserve_jumps int(4),
in reserve_packdate date,
in manufacturerid int(6),
in systemid int(6),
in stockid int(6),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Edit selected reserve - update parameters of a specific row
---------------------------------------------------------------------------------------------------
*/

begin

set @reserveid = reserveid;
set @reserve_model = reserve_model;
set @reserve_size = reserve_size;
set @reserve_sn = reserve_sn;
set @reserve_dom = reserve_dom;
set @reserve_jumps = reserve_jumps;
set @reserve_packdate = reserve_packdate;
set @manufacturerid = manufacturerid;
set @systemid = systemid;
set @stockid = stockid;
set @status = status;

update reserve_info ri
set
ri.reserve_model = @reserve_model,
ri.reserve_size = @reserve_size,
ri.reserve_sn = @reserve_sn,
ri.reserve_dom = @reserve_dom,
ri.reserve_jumps = @reserve_jumps,
ri.reserve_packdate = @reserve_packdate,
ri.manufacturerid = @manufacturerid,
ri.systemid = @systemid,
ri.stockid = @stockid,
ri.status = @status
where ri.reserveid = @reserveid;

end $$

drop procedure if exists editAAD $$

create procedure editAAD(
in aadid int(6),
in aad_model varchar(50),
in aad_sn varchar(20),
in aad_dom date,
in aad_jumps int(4),
in aad_nextregl date,
in aad_saved int(4),
in manufacturerid int(6),
in systemid int(6),
in stockid int(6),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Edit selected aad - update parameters of a specific row
---------------------------------------------------------------------------------------------------
*/

begin

set @aadid = aadid;
set @aad_model = aad_model;
set @aad_sn = aad_sn;
set @aad_dom = aad_dom;
set @aad_jumps = aad_jumps;
set @aad_nextregl = aad_nextregl;
set @aad_saved = aad_saved;
set @manufacturerid = manufacturerid;
set @systemid = systemid;
set @stockid = stockid;
set @status = status;

update aad_info ai
set
ai.aad_model = @aad_model,
ai.aad_sn = @aad_sn,
ai.aad_dom = @aad_dom,
ai.aad_jumps = @aad_jumps,
ai.aad_nextregl = @aad_nextregl,
ai.aad_saved = @aad_saved,
ai.manufacturerid = @manufacturerid,
ai.systemid = @systemid,
ai.stockid = @stockid,
ai.status = @status
where ai.aadid = @aadid;

end $$

drop procedure if exists editManufacturer $$

create procedure editManufacturer(
in manufacturerid int(6),
in manufacturer_name varchar(100),
in manufacturer_country varchar(100),
in manufacturer_telephone varchar(12),
in manufacturer_email varchar(50),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Edit selected manufacturer - update parameters of a specific row
---------------------------------------------------------------------------------------------------
*/

begin

set @manufacturerid = manufacturerid;
set @manufacturer_name = manufacturer_name;
set @manufacturer_country = manufacturer_country;
set @manufacturer_telephone = manufacturer_telephone;
set @manufacturer_email = manufacturer_email;
set @status = status;

update manufacturer_info mi
set
mi.manufacturer_name = @manufacturer_name,
mi.manufacturer_country = @manufacturer_country,
mi.manufacturer_telephone = @manufacturer_telephone,
mi.manufacturer_email = @manufacturer_email,
mi.status = @status
where mi.manufacturerid = @manufacturerid;

end $$

drop procedure if exists editStock $$

create procedure editStock(
in stockid int(6),
in stock_name varchar(50),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Edit selected stock - update parameters of a specific row
---------------------------------------------------------------------------------------------------
*/

begin

set @stockid = stockid;
set @stock_name = stock_name;
set @status = status;

update stock_info sti
set
sti.stock_name = @stock_name,
sti.status = @status
where sti.stockid = @stockid;

end $$

drop procedure if exists setStatusSkydiveSystem $$

create procedure setStatusSkydiveSystem(
in systemid int(6),
in canopyid int(6),
in reserveid int(6),
in aadid int(6),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Set status of selected skydive system (including canopy, reserve, aad)
where
0 - Active
1 - Deleted
2 - In repair
---------------------------------------------------------------------------------------------------
*/

begin

set @systemid = systemid;
set @canopyid = canopyid;
set @reserveid = reserveid;
set @aadid = aadid;
set @status = status;

update system_info si
set si.status = @status
where si.systemid = @systemid;

update canopy_info ci
set ci.status = @status
where ci.canopyid = @canopyid and ci.systemid = @systemid;

update reserve_info ri
set ri.status = @status
where ri.reserveid = @reserveid and ri.systemid = @systemid;

update aad_info ai
set ai.status = @status
where ai.aadid = @aadid and ai.systemid = @systemid;

end $$

drop procedure if exists setStatusContainer $$

create procedure setStatusContainer(
in systemid int(6),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Set status of selected containeer
where
0 - Active
1 - Deleted
2 - In repair
---------------------------------------------------------------------------------------------------
*/

begin

set @systemid = systemid;
set @status = status;

update system_info si
set si.status = @status
where si.systemid = @systemid;

end $$

drop procedure if exists setStatusCanopy $$

create procedure setStatusCanopy(
in canopyid int(6),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Set status of selected canopy
where
0 - Active
1 - Deleted
2 - In repair
---------------------------------------------------------------------------------------------------
*/

begin

set @canopyid = canopyid;
set @status = status;

update canopy_info ci
set ci.status = @status
where ci.canopyid = @canopyid;

end $$

drop procedure if exists setStatusReserve $$

create procedure setStatusReserve(
in reserveid int(6),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Set status of selected reserve
where
0 - Active
1 - Deleted
2 - In repair
---------------------------------------------------------------------------------------------------
*/

begin

set @reserveid = reserveid;
set @status = status;

update reserve_info ri
set ri.status = @status
where ri.reserveid = @reserveid;

end $$

drop procedure if exists setStatusAAD $$

create procedure setStatusAAD(
in aadid int(6),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Set status of selected aad
where
0 - Active
1 - Deleted
2 - In repair
---------------------------------------------------------------------------------------------------
*/

begin

set @aadid = aadid;
set @status = status;

update aad_info ai
set ai.status = @status
where ai.aadid = @aadid;

end $$

drop procedure if exists setStatusStock $$

create procedure setStatusStock(
in stockid int(6),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Set status of selected stock
where
0 - Active
1 - Deleted
---------------------------------------------------------------------------------------------------
*/

begin

set @stockid = stockid;
set @status = status;

update stock_info sti
set sti.status = @status
where sti.stockid = @stockid;

end $$

drop procedure if exists setStatusManufacturer $$

create procedure setStatusManufacturer(
in manufacturerid int(6),
in status int(1)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Set status of selected manufacturer
where
0 - Active
1 - Deleted
---------------------------------------------------------------------------------------------------
*/

begin

set @manufacturerid = manufacturerid;
set @status = status;

update manufacturer_info mi
set mi.status = @status
where mi.manufacturerid = @manufacturerid;

end $$

drop procedure if exists disassembleSkydiveSystem $$

create procedure disassembleSkydiveSystem(
in systemid int(6),
in canopyid int(6),
in reserveid int(6),
in aadid int(6)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Disassembles skydive system to elementary parts (containeer, canopy, reserve, aad)
---------------------------------------------------------------------------------------------------
*/

begin

set @systemid = systemid;
set @canopyid = canopyid;
set @reserveid = reserveid;
set @aadid = aadid;

update system_info si
set si.canopyid = 0, si.reserveid = 0, si.aadid = 0
where si.systemid = @systemid;

update canopy_info ci
set ci.systemid = 0
where ci.systemid = @systemid and ci.canopyid = @canopyid;

update reserve_info ri
set ri.systemid = 0
where ri.systemid = @systemid and ri.reserveid = @reserveid;

update aad_info ai
set ai.systemid = 0
where ai.systemid = @systemid and ai.aadid = @aadid;

end $$

drop procedure if exists assembleSkydiveSystem $$

create procedure assembleSkydiveSystem(
in systemid int(6),
in canopyid int(6),
in reserveid int(6),
in aadid int(6)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Assembles elementary parts (containeer, canopy, reserve, aad) to skydive system
---------------------------------------------------------------------------------------------------
*/

begin

set @systemid = systemid;
set @canopyid = canopyid;
set @reserveid = reserveid;
set @aadid = aadid;

update system_info si
set
si.canopyid = @canopyid,
si.reserveid = @reserveid,
si.aadid = @aadid
where si.systemid = @systemid;

update canopy_info ci
set ci.systemid = @systemid
where ci.systemid = 0 and ci.canopyid = @canopyid;

update reserve_info ri
set ri.systemid = @systemid
where ri.systemid = 0 and ri.reserveid = @reserveid;

update aad_info ai
set ai.systemid = @systemid
where ai.systemid = 0 and ai.aadid = @aadid;

end $$

drop procedure if exists replaceCanopy $$

create procedure replaceCanopy(
in systemid int(6),
in canopyid_old int(6),
in canopyid_new int(6)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Replaces canopies in assembled skydive system
---------------------------------------------------------------------------------------------------
*/

begin

set @systemid = systemid;
set @canopyid_old = canopyid_old;
set @canopyid_new = canopyid_new;

update system_info si
set si.canopyid = @canopyid_new
where si.systemid = @systemid and si.canopyid = @canopyid_old;

update canopy_info t1 join canopy_info t2
on t1.canopyid = @canopyid_old and t2.canopyid = @canopyid_new
set
t1.systemid = 0,
t2.systemid = @systemid;

end $$

drop procedure if exists replaceReserve $$

create procedure replaceReserve(
in systemid int(6),
in reserveid_old int(6),
in reserveid_new int(6)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Replaces reserves in assembled skydive system
---------------------------------------------------------------------------------------------------
*/

begin

set @systemid = systemid;
set @reserveid_old = reserveid_old;
set @reserveid_new = reserveid_new;

update system_info si
set si.reserveid = @reserveid_new
where si.systemid = @systemid and si.reserveid = @reserveid_old;

update reserve_info t1 join reserve_info t2
on t1.reserveid = @reserveid_old and t2.reserveid = @reserveid_new
set
t1.systemid = 0,
t2.systemid = @systemid;

end $$

drop procedure if exists replaceAAD $$

create procedure replaceAAD(
in systemid int(6),
in aadid_old int(6),
in aadid_new int(6)
)

/*
---------------------------------------------------------------------------------------------------
Author: Borodin Dmitriy
Created: 14.09.2018
Replaces aads in assembled skydive system
---------------------------------------------------------------------------------------------------
*/

begin

set @systemid = systemid;
set @aadid_old = aadid_old;
set @aadid_new = aadid_new;

update system_info si
set si.aadid = @aadid_new
where si.systemid = @systemid and si.aadid = @aadid_old;

update aad_info t1 join aad_info t2
on t1.aadid = @aadid_old and t2.aadid = @aadid_new
set
t1.systemid = 0,
t2.systemid = @systemid;

end $$

delimiter ;

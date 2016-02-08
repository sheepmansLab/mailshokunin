CREATE TABLE t_object_type
(
	 object_type_id INTEGER
	,object_type_name TEXT
	,object_class TEXT
	,PRIMARY KEY(object_type_id)
)
/
INSERT INTO t_object_type(object_type_id,object_type_name,object_class)
VALUES(0,"TextView","android.widget.TextView")
/
INSERT INTO t_object_type(object_type_id,object_type_name,object_class)
VALUES(1,"LayoutContentView","jp.sheepman.mailshokunin.view.LayoutContentEditTextView")
/
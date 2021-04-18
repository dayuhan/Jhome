namespace java com.rpc.common

#include "basedata.thrift"

/*基于thrift 编译器 生成IDL 代码*/
/*编译器：D:\rj\thrift\thrift.exe*/
/*thrift --gen java D:\EatingTools\XProject\Jhome\account-common\src\main\java\com\rpc\common\thrift\socketData.thrift*/
/*thrift --gen c++ D:\EatingTools\XProject\Jhome\account-common\src\main\java\com\rpc\common\thrift\socketData.thrift*/


/*IDL文件定义*/

typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

#Class
struct daxv{
1: optional String name
}
#异常
exception DataException{
1: optional String message,
2: optional String callStack,
3: optional String date
}

#接口
service socketService{
     void singleSend(1: required String param);
     void groupSend(1: required String param);
     String  getOnlineUserList();
}





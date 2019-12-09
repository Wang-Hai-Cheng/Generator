import sys


def replace(s, map):
    for m in map:
        s = s.replace(m, map[m])
    return s


entity = sys.argv[1]
entityService = entity + 'Service'
classEntity = entity.capitalize()
ClassEntityServiceImpl = classEntity + 'ServiceImpl'
package = 'cn.com.ty.lift.system'

template = """
import ${package}.entity.${classEntity};
import ${package}.service.impl.${ClassEntityServiceImpl};
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/${entity}")
public class ${classEntity}Controller {
    private final ${ClassEntityServiceImpl} ${entityService};

    @PostMapping("/list")
    public RestRespone list(@RequestBody ${classEntity} ${entity}) {
        return RestRespone.ok(${entityService}.page(${entity}.getPage()),"1","查询成功");
    }

    @PostMapping("/add")
    public RestRespone add(@RequestBody ${classEntity} ${entity}) {
        if (${entityService}.save(${entity})) {
            return RestRespone.ok(null,"1","新增成功");
        }
        return RestRespone.ok(null,"1","新增失败");
    }

    @PostMapping("/delete")
    public RestRespone delete(@RequestParam Integer id) {
        if (${entityService}.removeById(id)) {
            return RestRespone.ok(null,"1","删除成功");
        }
        return RestRespone.ok(null,"1","删除失败");
    }

    @PostMapping("/update")
    public RestRespone update(@RequestBody ${classEntity} ${entity}) {
        if (${entityService}.updateById(${entity})) {
            return RestRespone.ok(null,"1","更新成功");
        }
        return RestRespone.ok(null,"1","更新失败");
    }

    @GetMapping
    public RestRespone get(@RequestParam Integer id) {
        ${classEntity} byId = ${entityService}.getById(id);
        if (null == byId) {
            return return RestRespone.ok(null,"9","无数据");
        }
        return return RestRespone.ok(byid,"1","查询成功");;
    }
}

"""
template = replace(template, {"${classEntity}": classEntity, "${ClassEntityServiceImpl}": ClassEntityServiceImpl,
                              "${entity}": entity, "${entityService}": entityService})
# print(template)

open(classEntity + "Controller.java", 'w', encoding='utf8').write(template)

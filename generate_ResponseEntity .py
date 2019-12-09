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
    public ResponseEntity list(@RequestBody ${classEntity} ${entity}) {
        return ResponseEntity.status(HttpStatus.OK).body(${entityService}.page(${entity}.getPage()));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody ${classEntity} ${entity}) {
        if (${entityService}.save(${entity})) {
            return ResponseEntity.status(HttpStatus.OK).body("新增成功");
        }
        return ResponseEntity.status(HttpStatus.OK).body("新增失败");
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestParam Integer id) {
        if (${entityService}.removeById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("删除成功");
        }
        return ResponseEntity.status(HttpStatus.OK).body("删除失败");
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody ${classEntity} ${entity}) {
        if (${entityService}.updateById(${entity})) {
            return ResponseEntity.status(HttpStatus.OK).body("修改成功");
        }
        return ResponseEntity.status(HttpStatus.OK).body("修改失败");
    }

    @GetMapping
    public ResponseEntity get(@RequestParam Integer id) {
        ${classEntity} byId = ${entityService}.getById(id);
        if (null == byId) {
            return ResponseEntity.status(HttpStatus.OK).body("未找到");
        }
        return ResponseEntity.status(HttpStatus.OK).body(byId);
    }
}
"""
template = replace(template, {"${classEntity}": classEntity, "${ClassEntityServiceImpl}": ClassEntityServiceImpl,
                              "${entity}": entity, "${entityService}": entityService})
# print(template)

open(classEntity + "Controller.java", 'w', encoding='utf8').write(template)

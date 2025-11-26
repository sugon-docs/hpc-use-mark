
# 使用

- 跨节点
![[Pasted image 20250516102009.png]]
![[Pasted image 20250516102033.png]]

- 监控作业
先手动 sbatch 提交作业，图形监控
![[Pasted image 20250516103645.png]]
![[Pasted image 20250516103712.png]]
选 ade 文件监控

# 报错

- 打不开上层目录
```
麻烦给山东5区用户 acpapajmou 加一下acl权限，setfacl -m u:acpapajmou:r-x /public/home
```

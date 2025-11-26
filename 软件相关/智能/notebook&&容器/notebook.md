## 容器中 root 转普通

```bash
su xxx
```

## dcu 中安装轮子包 whl

>源链接：https://sourcefind.cn/#/service-list

```bash

# 环境要和whl module load 的一致先

pip install  pip==23.*  # 先执行这个
pip install xxxx.whl
```

# 切换内核

```bash
# root 下 conda创建一个环境

pip install ipykernel -i https://pypi.tuna.tsinghua.edu.cn/simple
pip install ipywidgets -i https://pypi.tuna.tsinghua.edu.cn/simple

#为jupyter添加内核
python -m ipykernel install --user --name=conda环境名 --display-name conda环境名
```
![[Pasted image 20250915134452.png]]
新终端生成的就是
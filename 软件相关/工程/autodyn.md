
# 报错

```bash
[proxy:0:0@g10r08] HYDU_create_process (../../utils/launch/launch.c:825): execvp error on file /work/home/acgpt9622o/apprepo/autodyn/2022r1-none/app/v221/autodyn/bin/autodyn221/linx64/../adworker221 (Not a directory)

# 商城模板有问题
vim /work/home/acgpt9622o/apprepo/autodyn/2022r1-none/scripts/env.sh
#里面加一下这个
sed -i '1s/\/autodyn221//g' parallel.cfg # 加一下这个

```
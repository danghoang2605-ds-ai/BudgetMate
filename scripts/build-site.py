"""Assemble the GitHub Pages site into _site/: copy site/ and convert the CI screenshots to WebP.

Usage: python scripts/build-site.py [out_dir]   (needs Pillow)
"""
import shutil
import sys
from pathlib import Path

from PIL import Image

ROOT = Path(__file__).resolve().parent.parent
OUT = Path(sys.argv[1]) if len(sys.argv) > 1 else ROOT / "_site"

shutil.rmtree(OUT, ignore_errors=True)
shutil.copytree(ROOT / "site", OUT)
screens = OUT / "screens"
screens.mkdir(exist_ok=True)
for png in sorted(p for p in (ROOT / "docs" / "screenshots").glob("*.png") if "-font" not in p.stem):
    im = Image.open(png).convert("RGB")
    for width in (360, 720):
        h = round(im.height * width / im.width)
        im.resize((width, h), Image.LANCZOS).save(screens / f"{png.stem}-{width}.webp", "WEBP", quality=82, method=6)
    print("converted", png.name)

# Detail crops for the month section, so it shows something the gallery does not.
# manage-days runs from the first day header to the end of the second day, so its height
# stays close to the chart's when the two sit side by side.
CROPS = {"stats-chart": ("stats.png", (0, 720, 1080, 1560)), "manage-days": ("manage.png", (0, 900, 1080, 1880))}
for name, (src, box) in CROPS.items():
    path = ROOT / "docs" / "screenshots" / src
    if path.exists():
        im = Image.open(path).convert("RGB").crop(box)
        # 1080 is the capture's own width: sharp at the ~580px the desktop layout shows on 2x screens.
        for width in (720, 1080):
            h = round(im.height * width / im.width)
            im.resize((width, h), Image.LANCZOS).save(screens / f"{name}-{width}.webp", "WEBP", quality=82, method=6)
        print("cropped", name)
